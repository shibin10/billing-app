package com.app.billingapi.service;

import com.app.billingapi.dto.SaleDto;
import com.app.billingapi.entity.Customer;
import com.app.billingapi.entity.Sale;
import com.app.billingapi.entity.User;
import com.app.billingapi.repository.CustomerRepository;
import com.app.billingapi.repository.SaleRepository;
import com.app.billingapi.repository.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SaleService {

	private static final Logger logger = LoggerFactory.getLogger(SaleService.class);

	private final SaleRepository saleRepository;
	private final UserRepository userRepository;
	private final CustomerRepository customerRepository;

	public SaleService(SaleRepository saleRepository, UserRepository userRepository,
			CustomerRepository customerRepository) {
		this.saleRepository = saleRepository;
		this.userRepository = userRepository;
		this.customerRepository = customerRepository;
	}

	public Sale createSale(SaleDto saleDto) {
		logger.info("Creating new Sale...");

		User staff = userRepository.findById(saleDto.getStaffId())
				.orElseThrow(() -> new IllegalArgumentException("Invalid staff (user) ID: " + saleDto.getStaffId()));

		Customer customer = customerRepository.findById(saleDto.getCustomerId())
				.orElseThrow(() -> new IllegalArgumentException("Invalid customer ID: " + saleDto.getCustomerId()));

		

		BigDecimal discountAmount = saleDto.getDiscountAmount() != null ? saleDto.getDiscountAmount() : BigDecimal.ZERO;

		Sale sale = new Sale();
		sale.setUserId(staff);
		sale.setCustomer(customer);
	
		sale.setPaymentMode(saleDto.getPaymentMode());
		sale.setPaymentStatus(saleDto.getPaymentStatus());
		sale.setBillType(saleDto.getBillType());
		sale.setSaleType(saleDto.getSaleType());
		sale.setTransactionId(saleDto.getTransactionId());
		sale.setSaleDate(LocalDate.now());
		Sale savedSale = saleRepository.save(sale);

		BigDecimal totalAmount = savedSale.getTotalAmount() != null ? savedSale.getTotalAmount() : BigDecimal.ZERO;
		BigDecimal finalAmount = totalAmount.subtract(discountAmount);
		
		savedSale.setFinalAmount(finalAmount);

		return saleRepository.save(savedSale);

	}

	public List<SaleDto> getAllSales() {
		return saleRepository.findAll().stream().map(this::mapToDto).collect(Collectors.toList());
	}

	public SaleDto getSaleById(Long id) {
		Sale sale = saleRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Sale not found with ID: " + id));
		return mapToDto(sale);
	}

	public SaleDto updateSale(SaleDto saleDto) {

		Sale sale = saleRepository.findById(saleDto.getSaleId())
				.orElseThrow(() -> new IllegalArgumentException("Sale not found with ID: " + saleDto.getSaleId()));

		if (saleDto.getStaffId() != null) {
			User staff = userRepository.findById(saleDto.getStaffId())
					.orElseThrow(() -> new IllegalArgumentException("Invalid staff ID"));
			sale.setUserId(staff);
		}

		if (saleDto.getCustomerId() != null) {
			Customer customer = customerRepository.findById(saleDto.getCustomerId())
					.orElseThrow(() -> new IllegalArgumentException("Invalid customer ID"));
			sale.setCustomer(customer);
		}

		

		sale.setTotalAmount(saleDto.getTotalAmount());
		sale.setTaxRate(saleDto.getTaxRate());
		sale.setFinalAmount(saleDto.getFinalAmount());
		sale.setPaymentMode(saleDto.getPaymentMode());
		sale.setPaymentStatus(saleDto.getPaymentStatus());
		sale.setTransactionId(saleDto.getTransactionId());

		Sale sales = saleRepository.save(sale);
		return mapToDto(sales);
	}

	public void deleteSale(Long id) {
		Sale sale = saleRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Sale not found with ID: " + id));
		saleRepository.delete(sale);
	}
	
	public Sale applyDiscount(Long saleId, BigDecimal discountAmount) {
	    Sale sale = saleRepository.findById(saleId)
	            .orElseThrow(() -> new IllegalArgumentException("Invalid sale ID: " + saleId));

	    BigDecimal totalAmount = sale.getTotalAmount() != null ? sale.getTotalAmount() : BigDecimal.ZERO;

	    sale.setDiscount(discountAmount);

	    BigDecimal finalAmount = totalAmount.subtract(discountAmount);
	    sale.setFinalAmount(finalAmount);

	    return saleRepository.save(sale);
	}



	private SaleDto mapToDto(Sale sale) {

		SaleDto saleDto = new SaleDto();

		saleDto.setSaleId(sale.getSaleId());
		saleDto.setStaffId(sale.getUserId() != null ? sale.getUserId().getUserId() : null);
		saleDto.setCustomerId(sale.getCustomer() != null ? sale.getCustomer().getCustomerId() : null);
		saleDto.setDiscountAmount(sale.getDiscount());
		saleDto.setTotalAmount(sale.getTotalAmount());
		saleDto.setTaxRate(sale.getTaxRate());
		saleDto.setFinalAmount(sale.getFinalAmount());
		saleDto.setPaymentMode(sale.getPaymentMode());
		saleDto.setPaymentStatus(sale.getPaymentStatus());
		saleDto.setTransactionId(sale.getTransactionId());
		saleDto.setSaleType(sale.getSaleType());
		saleDto.setBillType(sale.getBillType());
		
		

		return saleDto;
	}
}
