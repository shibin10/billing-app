package com.app.billingapi.service;

import com.app.billingapi.dto.InvoiceDto;
import com.app.billingapi.dto.ShopDto;
import com.app.billingapi.dto.SignupUserDto;
import com.app.billingapi.entity.*;
import com.app.billingapi.exception.InvoiceNotFoundException;
import com.app.billingapi.exception.UserIllegalArgumentException;
import com.app.billingapi.repository.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvoiceService {

	private static final Logger logger = LoggerFactory.getLogger(InvoiceService.class);

	private final InvoiceRepository invoiceRepository;
	private final CustomerRepository customerRepository;
	private final ShopRepository shopRepository;
	private final SaleRepository saleRepository;
	private final DiscountRepository discountRepository;
	private final UserRepository userRepository;

	public InvoiceService(InvoiceRepository invoiceRepository, CustomerRepository customerRepository,
			ShopRepository shopRepository, SaleRepository saleRepository, DiscountRepository discountRepository,
			UserRepository userRepository) {
		this.invoiceRepository = invoiceRepository;
		this.customerRepository = customerRepository;
		this.shopRepository = shopRepository;
		this.saleRepository = saleRepository;
		this.discountRepository = discountRepository;
		this.userRepository = userRepository;
	}

	public Invoice saveInvoice(InvoiceDto invoiceDto) {
		logger.info("Saving invoice");

		// Validations
		if (invoiceDto.getTotalAmount() == null) {
			throw new UserIllegalArgumentException("Missing total amount", "Total amount is required", 400);
		}

		try {
			String loggedInEmail = SecurityContextHolder.getContext().getAuthentication().getName();
			User user = userRepository.findByEmail(loggedInEmail)
					.orElseThrow(() -> new IllegalArgumentException("Logged in user not found"));

			Customer customer = customerRepository.findById(invoiceDto.getCustomerId())
					.orElseThrow(() -> new IllegalArgumentException("Invalid customer ID"));

			Shop shop = shopRepository.findById(invoiceDto.getShopId())
					.orElseThrow(() -> new IllegalArgumentException("Invalid shop ID"));

			Sale sale = saleRepository.findById(invoiceDto.getSalesId())
					.orElseThrow(() -> new IllegalArgumentException("Invalid sale ID"));

			Discount discount = null;
			if (invoiceDto.getDiscountId() != null) {
				discount = discountRepository.findById(invoiceDto.getDiscountId()).orElseThrow(
						() -> new IllegalArgumentException("Invalid discount ID: " + invoiceDto.getDiscountId()));
			}
			

			Invoice invoice = new Invoice();
			invoice.setCustomerId(customer);
			invoice.setShopId(shop);
			invoice.setSalesId(sale);
			invoice.setDiscountId(discount);
			invoice.setUserId(user);
			invoice.setTotalAmount(invoiceDto.getTotalAmount());
			invoice.setTax(invoiceDto.getTax());
			invoice.setDueDate(invoiceDto.getDueDate());
			invoice.setPaymentStatus(invoiceDto.getPaymentStatus());
			invoice.setPaymentMode(invoiceDto.getPaymentMode());
			invoice.setRemark(invoiceDto.getRemark());

			return invoiceRepository.save(invoice);
			
		} catch (Exception e) {
			logger.error("Error saving invoice", e);
			throw new RuntimeException("Failed to save invoice", e);
		}
	}

	public List<InvoiceDto> findAllInvoices() {
		logger.info("Fetching all invoices");
		try {
			return invoiceRepository.findAll().stream().map(this::mapToInvoiceDto).collect(Collectors.toList());
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

	public InvoiceDto updateInvoice(InvoiceDto invoiceDto) {
		logger.info("Updating invoice ID: {}", invoiceDto.getInvoiceId());

		Invoice invoice = invoiceRepository.findById(invoiceDto.getInvoiceId())
				.orElseThrow(() -> new InvoiceNotFoundException("Invoice not found", "Invalid invoice ID", 404));

		invoice.setTotalAmount(invoiceDto.getTotalAmount());
		invoice.setTax(invoiceDto.getTax());
		invoice.setDueDate(invoiceDto.getDueDate());
		invoice.setPaymentStatus(invoiceDto.getPaymentStatus());
		invoice.setPaymentMode(invoiceDto.getPaymentMode());
		invoice.setRemark(invoiceDto.getRemark());

		Invoice invoices = invoiceRepository.save(invoice);
		return mapToInvoiceDto(invoices);
	}

	public void deleteInvoice(Long id) {
		logger.info("Deleting invoice ID: {}", id);
		Invoice invoice = invoiceRepository.findById(id)
				.orElseThrow(() -> new InvoiceNotFoundException("Invoice not found", "Invalid invoice ID", 404));
		invoiceRepository.delete(invoice);
	}

	private InvoiceDto mapToInvoiceDto(Invoice invoice) {
		InvoiceDto dto = new InvoiceDto();
		dto.setInvoiceId(invoice.getInvoiceId());
		dto.setCustomerId(invoice.getCustomerId().getCustomerId());
		dto.setShopId(invoice.getShopId().getShopId());
		dto.setSalesId(invoice.getSalesId().getSaleId());
		// dto.setDiscountId(invoice.getDiscountId().getDiscountId());
		dto.setTotalAmount(invoice.getTotalAmount());
		dto.setTax(invoice.getTax());
		dto.setDueDate(invoice.getDueDate());
		dto.setPaymentStatus(invoice.getPaymentStatus());
		dto.setPaymentMode(invoice.getPaymentMode());
		dto.setRemark(invoice.getRemark());

		// Optional: Map shop and user details like in your CustomerService
		Shop shop = invoice.getShopId();
		if (shop != null) {
			ShopDto shopDto = new ShopDto();
			shopDto.setShopId(shop.getShopId());
			shopDto.setName(shop.getName());
			shopDto.setPlace(shop.getPlace());
			shopDto.setStatus(shop.getStatus());
			shopDto.setMap(shop.getMap());
			dto.setShop(shopDto);

			User user = shop.getOwnerId();
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
		}

		return dto;
	}
}
