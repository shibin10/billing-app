package com.app.billingapi.service;

import com.app.billingapi.dto.CustomerDto;
import com.app.billingapi.dto.InvoiceDto;
import com.app.billingapi.dto.ProductDto;
import com.app.billingapi.dto.SaleDto;
import com.app.billingapi.dto.SaleItemDto;
import com.app.billingapi.dto.ShopDto;
import com.app.billingapi.dto.SignupUserDto;
import com.app.billingapi.entity.*;
import com.app.billingapi.enums.BillType;
import com.app.billingapi.enums.PaymentStatus;
import com.app.billingapi.enums.SaleType;
import com.app.billingapi.exception.InvoiceNotFoundException;
import com.app.billingapi.repository.*;

import jakarta.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvoiceService {

	private static final Logger logger = LoggerFactory.getLogger(InvoiceService.class);

	private final InvoiceRepository invoiceRepository;
	private final CustomerRepository customerRepository;
	private final ShopRepository shopRepository;
	private final SaleRepository saleRepository;
	private final SaleItemRepository saleItemRepository;
	private final UserRepository userRepository;
	private final ProductRepository productRepository;

	public InvoiceService(InvoiceRepository invoiceRepository, CustomerRepository customerRepository,
			ShopRepository shopRepository, SaleRepository saleRepository, UserRepository userRepository,
			ProductRepository productRepository, SaleItemRepository saleItemRepository) {
		this.invoiceRepository = invoiceRepository;
		this.customerRepository = customerRepository;
		this.shopRepository = shopRepository;
		this.saleRepository = saleRepository;
		this.saleItemRepository = saleItemRepository;
		this.userRepository = userRepository;
		this.productRepository = productRepository;
	}

	@Transactional
	public InvoiceDto saveInvoice(InvoiceDto invoiceDto) {
		logger.info("Saving invoice with items");

		String loggedInEmail = SecurityContextHolder.getContext().getAuthentication().getName();

		User staff = userRepository.findByEmail(loggedInEmail)
				.orElseThrow(() -> new IllegalArgumentException("Logged in user not found"));

		Customer customer = customerRepository.findById(invoiceDto.getCustomerId())
				.orElseThrow(() -> new IllegalArgumentException("Invalid customer ID"));

		Shop shop = shopRepository.findById(invoiceDto.getShopId())
				.orElseThrow(() -> new IllegalArgumentException("Invalid shop ID"));

		BigDecimal roundOff = invoiceDto.getDiscount() != null ? invoiceDto.getDiscount() : BigDecimal.ZERO;

		Sale sale = new Sale();

		sale.setUserId(staff);
		sale.setCustomer(customer);
		sale.setPaymentMode(invoiceDto.getPaymentMode());
		sale.setPaymentStatus(invoiceDto.getPaymentStatus());
		sale.setBillType(BillType.valueOf(invoiceDto.getBillType().toUpperCase()));
		sale.setSaleType(SaleType.valueOf(invoiceDto.getSaleType().toUpperCase()));
		sale.setTransactionId(invoiceDto.getTransactionId());
		sale.setShopId(shop);
		sale.setSaleDate(LocalDate.now());
		sale.setDiscount(roundOff);

		Sale savedSale = saleRepository.save(sale);

		BigDecimal totalItemDiscount = BigDecimal.ZERO;
		BigDecimal totalAmount = BigDecimal.ZERO;
		BigDecimal totalTax = BigDecimal.ZERO;

		for (SaleItemDto itemDto : invoiceDto.getSaleItems()) {

			Product product = productRepository.findById(itemDto.getProductId())
					.orElseThrow(() -> new IllegalArgumentException("Invalid product ID: " + itemDto.getProductId()));

			BigDecimal price = product.getRetailRate();
			BigDecimal quantity = itemDto.getQuantity();
			BigDecimal itemDiscount = itemDto.getDiscount() != null ? itemDto.getDiscount() : BigDecimal.ZERO;
			BigDecimal taxRate = product.getTaxRate() != null ? product.getTaxRate() : BigDecimal.ZERO;

			BigDecimal grossAmount = price.multiply(quantity);
			BigDecimal taxAmount = grossAmount.multiply(taxRate).divide(BigDecimal.valueOf(100));
			BigDecimal total = grossAmount.subtract(itemDiscount).add(taxAmount);

			SaleItem item = new SaleItem();
			item.setSaleId(savedSale);
			item.setProduct(product);
			item.setQuantity(quantity);
			item.setPrice(price);
			item.setDiscount(itemDiscount);
			item.setTax(taxAmount);
			item.setTotal(total);

			saleItemRepository.save(item);

			totalItemDiscount = totalItemDiscount.add(itemDiscount);
			totalAmount = totalAmount.add(total);
			totalTax = totalTax.add(taxAmount);
		}

		BigDecimal finalAmount = totalAmount.subtract(roundOff);

		savedSale.setTotalAmount(totalAmount);
		savedSale.setFinalAmount(finalAmount);
		savedSale.setTaxRate(totalTax);
		saleRepository.save(savedSale);

		// Create Invoice
		String invoiceNo = generateInvoiceNumber();

		Invoice invoice = new Invoice();
		invoice.setInvoiceNo(invoiceNo);
		invoice.setInvoiceDate(invoiceDto.getInvoiceDate());
		invoice.setCustomerId(customer);
		invoice.setShopId(shop);
		invoice.setSalesId(savedSale);

		BigDecimal totalDiscount = totalItemDiscount.add(roundOff);
		invoice.setDiscount(totalDiscount);

		invoice.setUserId(staff);
		invoice.setTotalAmount(finalAmount);
		invoice.setTax(totalTax);
		invoice.setDueDate(invoiceDto.getDueDate());

		if (invoiceDto.getDueDate() != null) {
			invoice.setPaymentStatus(PaymentStatus.PENDING);
		} else {
			invoice.setPaymentStatus(invoiceDto.getPaymentStatus());
		}

		invoice.setPaymentMode(invoiceDto.getPaymentMode());
		invoice.setRemark(invoiceDto.getRemark());
		invoice.setAmountPaid(invoiceDto.getAmountPaid());

		Invoice savedInvoice = invoiceRepository.save(invoice);

		BigDecimal spend = finalAmount;
		BigDecimal currentSpend = customer.getTotalSpend() != null ? customer.getTotalSpend() : BigDecimal.ZERO;
		Integer currentPts = customer.getLoyaltyPoints() != null ? customer.getLoyaltyPoints() : 0;

		customer.setTotalSpend(currentSpend.add(spend));

		int earnedPts = spend.divide(BigDecimal.valueOf(100), RoundingMode.FLOOR).intValue();
		customer.setLoyaltyPoints(currentPts + earnedPts);

		customerRepository.save(customer);
		return mapToInvoiceDto(savedInvoice);
	}

	public List<InvoiceDto> findAllInvoices() {
		logger.info("Fetching all invoices");
		try {
			List<Invoice> invoice =invoiceRepository.findAll();
			return invoice.stream().map(this::mapToInvoiceDto).collect(Collectors.toList());
		} catch (Exception e) {
			logger.error("Error fetching invoices", e);
			throw new RuntimeException("Failed to fetch invoices", e);
		}
	}

	public InvoiceDto findInvoiceById(Long id) {
		logger.info("Finding invoice by ID: {}", id);
		return invoiceRepository.findById(id).map(this::mapToInvoiceDto)
				.orElseThrow(() -> new InvoiceNotFoundException("Invoice not found with ID: " + id, "Invalid ID", 404));
	}
	
	@Transactional
	public InvoiceDto updateInvoice(InvoiceDto invoiceDto) {
		logger.info("Updating invoice with ID: {}", invoiceDto.getInvoiceId());

		Invoice invoice = invoiceRepository.findById(invoiceDto.getInvoiceId())
				.orElseThrow(() -> new IllegalArgumentException("Invoice not found"));

		Sale existingSale = invoice.getSalesId();

		Customer customer = customerRepository.findById(invoiceDto.getCustomerId())
				.orElseThrow(() -> new IllegalArgumentException("Invalid customer ID"));

		Shop shop = shopRepository.findById(invoiceDto.getShopId())
				.orElseThrow(() -> new IllegalArgumentException("Invalid shop ID"));

		String loggedInEmail = SecurityContextHolder.getContext().getAuthentication().getName();
		User staff = userRepository.findByEmail(loggedInEmail)
				.orElseThrow(() -> new IllegalArgumentException("Logged in user not found"));

		BigDecimal roundOff = invoiceDto.getDiscount() != null ? invoiceDto.getDiscount() : BigDecimal.ZERO;

		// Delete old sale items
		List<SaleItem> existingItems = saleItemRepository.findBySaleId_SaleId(existingSale.getSaleId());
		for (SaleItem item : existingItems) {
			saleItemRepository.delete(item);
		}

		// Reset totals
		BigDecimal totalItemDiscount = BigDecimal.ZERO;
		BigDecimal totalAmount = BigDecimal.ZERO;
		BigDecimal totalTax = BigDecimal.ZERO;

		// Add new sale items
		for (SaleItemDto itemDto : invoiceDto.getSaleItems()) {

			Product product = productRepository.findById(itemDto.getProductId())
					.orElseThrow(() -> new IllegalArgumentException("Invalid product ID: " + itemDto.getProductId()));

			BigDecimal price = product.getRetailRate();
			BigDecimal quantity = itemDto.getQuantity();
			BigDecimal itemDiscount = itemDto.getDiscount() != null ? itemDto.getDiscount() : BigDecimal.ZERO;
			BigDecimal taxRate = product.getTaxRate() != null ? product.getTaxRate() : BigDecimal.ZERO;

			BigDecimal grossAmount = price.multiply(quantity);
			BigDecimal taxAmount = grossAmount.multiply(taxRate).divide(BigDecimal.valueOf(100));
			BigDecimal total = grossAmount.subtract(itemDiscount).add(taxAmount);

			SaleItem item = new SaleItem();
			item.setSaleId(existingSale);
			item.setProduct(product);
			item.setQuantity(quantity);
			item.setPrice(price);
			item.setDiscount(itemDiscount);
			item.setTax(taxAmount);
			item.setTotal(total);

			saleItemRepository.save(item);

			totalItemDiscount = totalItemDiscount.add(itemDiscount);
			totalAmount = totalAmount.add(total);
			totalTax = totalTax.add(taxAmount);
		}

		// Update Sale
		BigDecimal finalAmount = totalAmount.subtract(roundOff);
		existingSale.setCustomer(customer);
		existingSale.setShopId(shop);
		existingSale.setPaymentMode(invoiceDto.getPaymentMode());
		existingSale.setPaymentStatus(invoiceDto.getPaymentStatus());
		existingSale.setBillType(BillType.valueOf(invoiceDto.getBillType().toUpperCase()));
		existingSale.setSaleType(SaleType.valueOf(invoiceDto.getSaleType().toUpperCase()));
		existingSale.setTransactionId(invoiceDto.getTransactionId());
		existingSale.setDiscount(roundOff);
		existingSale.setSaleDate(LocalDate.now());
		existingSale.setTotalAmount(totalAmount);
		existingSale.setFinalAmount(finalAmount);
		existingSale.setTaxRate(totalTax);
		existingSale.setUserId(staff);

		saleRepository.save(existingSale);

		// Update Invoice
		invoice.setInvoiceDate(invoiceDto.getInvoiceDate());
		invoice.setCustomerId(customer);
		invoice.setShopId(shop);
		invoice.setSalesId(existingSale);
		invoice.setDiscount(totalItemDiscount.add(roundOff));
		invoice.setUserId(staff);
		invoice.setTotalAmount(finalAmount);
		invoice.setTax(totalTax);
		invoice.setDueDate(invoiceDto.getDueDate());

		if (invoiceDto.getDueDate() != null) {
			invoice.setPaymentStatus(PaymentStatus.PENDING);
		} else {
			invoice.setPaymentStatus(invoiceDto.getPaymentStatus());
		}

		invoice.setPaymentMode(invoiceDto.getPaymentMode());
		invoice.setRemark(invoiceDto.getRemark());
		invoice.setAmountPaid(invoiceDto.getAmountPaid());

		Invoice updatedInvoice = invoiceRepository.save(invoice);

		// Update Customer Loyalty (optional: adjust based on business rules)
		BigDecimal currentSpend = customer.getTotalSpend() != null ? customer.getTotalSpend() : BigDecimal.ZERO;
		Integer currentPts = customer.getLoyaltyPoints() != null ? customer.getLoyaltyPoints() : 0;

		customer.setTotalSpend(currentSpend.add(finalAmount)); // Adjust if needed
		int earnedPts = finalAmount.divide(BigDecimal.valueOf(100), RoundingMode.FLOOR).intValue();
		customer.setLoyaltyPoints(currentPts + earnedPts);

		customerRepository.save(customer);

		return mapToInvoiceDto(updatedInvoice);
	}


	@Transactional
	public void deleteInvoice(Long invoiceId) {
		Invoice invoice = invoiceRepository.findById(invoiceId)
				.orElseThrow(() -> new IllegalArgumentException("Invoice not found"));

		Sale sale = invoice.getSalesId();
		Customer customer = invoice.getCustomerId();

		BigDecimal spendToReverse = invoice.getTotalAmount() != null ? invoice.getTotalAmount() : BigDecimal.ZERO;
		int pointsToReverse = spendToReverse.divide(BigDecimal.valueOf(100), RoundingMode.FLOOR).intValue();

		customer.setTotalSpend(customer.getTotalSpend().subtract(spendToReverse).max(BigDecimal.ZERO));
		customer.setLoyaltyPoints(Math.max(customer.getLoyaltyPoints() - pointsToReverse, 0));

		customerRepository.save(customer);

		invoiceRepository.delete(invoice);

		saleRepository.delete(sale);
	}

	private InvoiceDto mapToInvoiceDto(Invoice invoice) {
		InvoiceDto dto = new InvoiceDto();

		dto.setInvoiceNo(invoice.getInvoiceNo());
		dto.setInvoiceId(invoice.getInvoiceId());
		dto.setInvoiceDate(invoice.getInvoiceDate());
		dto.setCustomerId(invoice.getCustomerId().getCustomerId());
		dto.setCustomerName(invoice.getCustomerId().getName());
		dto.setShopId(invoice.getShopId().getShopId());
		dto.setSalesId(invoice.getSalesId().getSaleId());
		dto.setStaffName(invoice.getUserId().getFullName());
		dto.setTotalAmount(invoice.getTotalAmount());
		dto.setAmountPaid(invoice.getAmountPaid());
		dto.setDueDate(invoice.getDueDate());
		dto.setDiscount(invoice.getDiscount());
		dto.setTax(invoice.getTax());
		dto.setDueDate(invoice.getDueDate());
		dto.setPaymentStatus(invoice.getPaymentStatus());
		dto.setPaymentMode(invoice.getPaymentMode());
		dto.setRemark(invoice.getRemark());
		dto.setRemark(invoice.getRemark());

		List<SaleItem> items = saleItemRepository.findBySaleId_SaleId(invoice.getSalesId().getSaleId());

		List<SaleItemDto> itemDtos = items.stream().map(item -> {
			SaleItemDto itemDto = new SaleItemDto();

			itemDto.setSaleItemId(item.getSaleItemId());

			Product product = item.getProduct();
			ProductDto productDto = new ProductDto();

			productDto.setProductId(product.getProductId());
			productDto.setName(product.getName());
			productDto.setProductNumber(product.getProductNumber());
			productDto.setHsn(product.getHsn());
			productDto.setRetailRate(product.getRetailRate());
			productDto.setPurchasePrice(product.getPurchasePrice());

			productDto.setCGST(product.getCgst());
			productDto.setSGST(product.getSgst());
			productDto.setTaxRate(product.getTaxRate());

			itemDto.setProduct(productDto);
			itemDto.setQuantity(item.getQuantity());
			itemDto.setPrice(item.getPrice());
			itemDto.setTax(item.getTax());
			itemDto.setDiscount(item.getDiscount());
			itemDto.setTotal(item.getTotal());

			return itemDto;
		}).collect(Collectors.toList());

		dto.setSaleItems(itemDtos);

		Sale sale = invoice.getSalesId();
		if (sale != null) {

			SaleDto saleDto = new SaleDto();
			saleDto.setSaleId(sale.getSaleId());
			dto.setStaffName(invoice.getUserId().getFullName());
			saleDto.setBillType(sale.getBillType());
			saleDto.setSaleType(sale.getSaleType());
			saleDto.setSaleDate(sale.getSaleDate());
			saleDto.setTotalAmount(sale.getTotalAmount());
			saleDto.setFinalAmount(sale.getFinalAmount());
			saleDto.setDiscountAmount(sale.getDiscount());
			saleDto.setTaxRate(sale.getTaxRate());
			saleDto.setPaymentMode(sale.getPaymentMode());
			saleDto.setPaymentStatus(sale.getPaymentStatus());

			dto.setSales(saleDto);

		}

		Shop shop = invoice.getShopId();
		if (shop != null) {
			ShopDto shopDto = new ShopDto();
			shopDto.setShopId(shop.getShopId());
			shopDto.setName(shop.getName());
			shopDto.setPlace(shop.getPlace());
			shopDto.setAddress(shop.getAddress());
			shopDto.setPhone(shop.getPhone());
			shopDto.setGstNo(shop.getGstNo());
			shopDto.setStatus(shop.getStatus());
			shopDto.setMap(shop.getMap());
			dto.setShop(shopDto);

			User user = shop.getOwner();
			if (user != null) {
				SignupUserDto userDto = new SignupUserDto();
				userDto.setUserId(user.getUserId());
				userDto.setFullName(user.getFullName());
				userDto.setEmail(user.getEmail());
				userDto.setPlace(user.getPlace());
				userDto.setPhone(user.getPhone());
				userDto.setStatus(user.getStatus());

				shopDto.setOwner(userDto);
			}

			Customer customer = invoice.getCustomerId();
			if (customer != null) {
				CustomerDto customerDto = new CustomerDto();
				customerDto.setName(customer.getName());
				customerDto.setPhone(customer.getPhone());
				customerDto.setPlace(customer.getPlace());
				customerDto.setTotalSpend(customerDto.getTotalSpend());

				dto.setCustomer(customerDto);
			}
		}

		return dto;
	}

	private String generateInvoiceNumber() {
	    Invoice lastInvoice = invoiceRepository.findTopByOrderByInvoiceIdDesc();

	    int nextNumber = 482; // Start from 482
	    if (lastInvoice != null && lastInvoice.getInvoiceNo() != null) {
	        String lastNo = lastInvoice.getInvoiceNo().replaceAll("[^0-9]", ""); // Extract number
	        try {
	            nextNumber = Integer.parseInt(lastNo) + 1;
	        } catch (NumberFormatException e) {
	            nextNumber = 482; // fallback to 482 if parse fails
	        }
	    }

	    return String.format("INV%04d", nextNumber); // example: INV0482, INV0483...
	}


}
