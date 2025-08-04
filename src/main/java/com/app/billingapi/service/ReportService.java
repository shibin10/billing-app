package com.app.billingapi.service;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.app.billingapi.dto.DiscountSummaryDto;
import com.app.billingapi.dto.InventoryMovementDto;
import com.app.billingapi.dto.ProfitSummaryDto;
import com.app.billingapi.dto.SalesSummaryDto;
import com.app.billingapi.dto.TimeInsightDto;
import com.app.billingapi.dto.TopCustomerDto;
import com.app.billingapi.dto.TopProductDto;
import com.app.billingapi.entity.Invoice;
import com.app.billingapi.entity.Product;
import com.app.billingapi.entity.Sale;
import com.app.billingapi.entity.SaleItem;
import com.app.billingapi.entity.Shop;
import com.app.billingapi.entity.User;
import com.app.billingapi.repository.InvoiceRepository;
import com.app.billingapi.repository.ProductRepository;
import com.app.billingapi.repository.SaleItemRepository;
import com.app.billingapi.repository.SaleRepository;
import com.app.billingapi.repository.ShopRepository;
import com.app.billingapi.repository.UserRepository;

import java.time.temporal.WeekFields;

@Service
public class ReportService {

	@Autowired
	private SaleItemRepository saleItemRepository;
	@Autowired
	private InvoiceRepository invoiceRepository;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private SaleRepository saleRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ShopRepository shopRepository;

	public List<TopProductDto> getTopSellingProducts(Long shopId) {
		List<SaleItem> items = saleItemRepository.findBySaleId_ShopId_ShopId(shopId);
		return items.stream()
				.collect(Collectors.groupingBy(i -> i.getProduct().getName(),
						Collectors.summingDouble(i -> i.getQuantity().doubleValue())))
				.entrySet().stream().sorted(Map.Entry.<String, Double>comparingByValue().reversed()).limit(5)
				.map(e -> new TopProductDto(e.getKey(), e.getValue())).collect(Collectors.toList());
	}

	public List<TopCustomerDto> getTopCustomers(Long shopId) {
		List<Invoice> invoices = invoiceRepository.findByShopId_ShopId(shopId);
		return invoices.stream()
				.collect(Collectors.groupingBy(i -> i.getCustomerId().getName(),
						Collectors.summingDouble(i -> i.getTotalAmount().doubleValue())))
				.entrySet().stream().sorted(Map.Entry.<String, Double>comparingByValue().reversed()).limit(5)
				.map(e -> new TopCustomerDto(e.getKey(), BigDecimal.valueOf(e.getValue())))
				.collect(Collectors.toList());
	}

	public List<SalesSummaryDto> getSalesSummary(Long shopId, LocalDate from, LocalDate to, String period) {
		List<Invoice> invoices = invoiceRepository.findByShopId_ShopIdAndInvoiceDateBetween(shopId, from, to);
		Map<String, BigDecimal> summaryMap = new LinkedHashMap<>();

		for (Invoice invoice : invoices) {
			LocalDate invoiceDate = invoice.getInvoiceDate();
			if (invoiceDate == null)
				continue;

			String key;
			if ("WEEKLY".equalsIgnoreCase(period)) {
				int week = invoiceDate.get(WeekFields.ISO.weekOfWeekBasedYear());
				int year = invoiceDate.getYear();
				key = "Week-" + week + "-" + year;
			} else if ("MONTHLY".equalsIgnoreCase(period)) {
				key = invoiceDate.getMonth().toString() + "-" + invoiceDate.getYear();
			} else {
				key = invoiceDate.toString(); // daily
			}

			summaryMap.merge(key, invoice.getTotalAmount(), BigDecimal::add);
		}

		return summaryMap.entrySet().stream().map(e -> new SalesSummaryDto(e.getKey(), e.getValue()))
				.collect(Collectors.toList());
	}

	/*
	 * public TaxSummaryDto getTaxSummary(Long shopId, LocalDate from, LocalDate to)
	 * { List<SaleItem> items =
	 * saleItemRepository.findBySaleId_ShopId_ShopIdAndSaleId_SaleDateBetween(
	 * shopId, from, to); BigDecimal cgst = BigDecimal.ZERO; BigDecimal sgst =
	 * BigDecimal.ZERO; BigDecimal igst = BigDecimal.ZERO;
	 * 
	 * for (SaleItem item : items) { if (item.getTax() != null && item.getTaxType()
	 * != null) { switch (item.getTaxType()) { case CGST: cgst =
	 * cgst.add(item.getTax()); break; case SGST: sgst = sgst.add(item.getTax());
	 * break; case IGST: igst = igst.add(item.getTax()); break; } } } return new
	 * TaxSummaryDto(cgst, sgst, igst); }
	 */

	public List<DiscountSummaryDto> getDiscountSummary(Long shopId, LocalDate from, LocalDate to) {
		List<SaleItem> items = saleItemRepository.findBySaleId_ShopId_ShopIdAndSaleId_SaleDateBetween(shopId, from, to);
		return items.stream().filter(i -> i.getDiscount() != null && i.getDiscount().compareTo(BigDecimal.ZERO) > 0)
				.map(i -> new DiscountSummaryDto(i.getProduct().getName(), i.getDiscount()))
				.collect(Collectors.toList());
	}

	public List<InventoryMovementDto> getInventoryMovement(Long shopId, LocalDate from, LocalDate to) {
		List<SaleItem> items = saleItemRepository.findBySaleId_ShopId_ShopIdAndSaleId_SaleDateBetween(shopId, from, to);
		return items.stream()
				.collect(Collectors.groupingBy(i -> i.getProduct().getName(),
						Collectors.summingDouble(i -> i.getQuantity().doubleValue())))
				.entrySet().stream().map(e -> new InventoryMovementDto(e.getKey(), e.getValue()))
				.collect(Collectors.toList());
	}

	public TimeInsightDto getTimeInsights(Long shopId, LocalDate from, LocalDate to) {
		List<Invoice> invoices = invoiceRepository.findByShopId_ShopIdAndInvoiceDateBetween(shopId, from, to);

		Map<Integer, Long> hourMap = new HashMap<>();
		Map<DayOfWeek, Long> dayMap = new HashMap<>();

		for (Invoice invoice : invoices) {
			if (invoice.getSalesId() != null && invoice.getSalesId().getCreatedAt() != null) {
				LocalDateTime dateTime = invoice.getSalesId().getCreatedAt().toInstant().atZone(ZoneId.systemDefault())
						.toLocalDateTime();

				int hour = dateTime.getHour();
				hourMap.put(hour, hourMap.getOrDefault(hour, 0L) + 1);

				DayOfWeek day = dateTime.getDayOfWeek();
				dayMap.put(day, dayMap.getOrDefault(day, 0L) + 1);
			}
		}
		int peakHour = hourMap.entrySet().stream().max(Map.Entry.comparingByValue()).map(Map.Entry::getKey).orElse(-1);
		DayOfWeek bestDay = dayMap.entrySet().stream().max(Map.Entry.comparingByValue()).map(Map.Entry::getKey)
				.orElse(null);
		DayOfWeek worstDay = dayMap.entrySet().stream().min(Map.Entry.comparingByValue()).map(Map.Entry::getKey)
				.orElse(null);

		return new TimeInsightDto(peakHour, bestDay, worstDay);
	}

	public List<Product> getDeadStockProducts(Long shopId, LocalDate from, LocalDate to) {
		List<SaleItem> soldItems = saleItemRepository.findDistinctBySaleId_ShopId_ShopIdAndSaleId_SaleDateBetween(shopId,
				from, to);

		List<Long> soldProductIds = soldItems.stream().map(item -> item.getProduct().getProductId()).distinct()
				.collect(Collectors.toList());

		if (soldProductIds.isEmpty()) {
			return productRepository.findByShopId_ShopId(shopId);
		} else {
			return productRepository.findByShopId_ShopIdAndProductIdNotIn(shopId, soldProductIds);
		}

	}
	
	public ProfitSummaryDto getProfitBetweenDates(LocalDate startDate, LocalDate endDate) {
	   
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
	    User user = userRepository.findByEmail(email)
	            .orElseThrow(() -> new RuntimeException("User not found"));

	    Shop shop = shopRepository.findByOwner_UserId(user.getUserId())
	            .stream()
	            .findFirst()
	            .orElseThrow(() -> new RuntimeException("No shop found for user"));

	    List<Sale> sales = saleRepository.findByShopId_ShopIdAndSaleDateBetween(
	            shop.getShopId(), startDate, endDate
	    );

	    if (sales.isEmpty()) {
	        return new ProfitSummaryDto(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
	    }

	    List<Long> saleIds = sales.stream()
	            .map(Sale::getSaleId)
	            .collect(Collectors.toList());

	    List<SaleItem> items = saleItemRepository.findBySaleId_SaleIdIn(saleIds);

	    BigDecimal totalRevenue = BigDecimal.ZERO;
	    BigDecimal totalCost = BigDecimal.ZERO;

	    for (SaleItem item : items) {
	        BigDecimal quantity = item.getQuantity();
	        Product product = item.getProduct();

	        BigDecimal sellingRate = item.getPrice();  // or product.getRetailRate()
	        BigDecimal purchaseRate = product.getPurchasePrice();

	        totalRevenue = totalRevenue.add(sellingRate.multiply(quantity));
	        totalCost = totalCost.add(purchaseRate.multiply(quantity));
	    }
	    
	    List<Invoice> invoices = invoiceRepository
	            .findByShopId_ShopIdAndInvoiceDateBetween(
	                    shop.getShopId(), startDate, endDate);

	    BigDecimal totalDiscount = invoices.stream()
	                                       .map(Invoice::getDiscount)
	                                       .filter(d -> d != null)
	                                       .reduce(BigDecimal.ZERO, BigDecimal::add);

	    /* ---------- Profit after discount ---------- */
	    BigDecimal netProfit = totalRevenue
	                             .subtract(totalCost)
	                             .subtract(totalDiscount);


	    ProfitSummaryDto dto = new ProfitSummaryDto();
	    
	    dto.setTotalRevenue(totalRevenue);
	    dto.setTotalCost(totalCost);
	    dto.setTotalDiscount(totalDiscount);
	    dto.setTotalProfit(netProfit);

	    return dto;
	}



}
