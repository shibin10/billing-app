package com.app.billingapi.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.billingapi.dto.DiscountSummaryDto;
import com.app.billingapi.dto.InventoryMovementDto;
import com.app.billingapi.dto.ProfitSummaryDto;
import com.app.billingapi.dto.SalesSummaryDto;
import com.app.billingapi.dto.TimeInsightDto;
import com.app.billingapi.dto.TopCustomerDto;
import com.app.billingapi.dto.TopProductDto;
import com.app.billingapi.entity.Product;
import com.app.billingapi.service.ReportService;

@RestController
@RequestMapping("/reports")
public class ReportController {

	@Autowired
	private ReportService reportService;

	@GetMapping("/shop/{shopId}/top-products")
	public List<TopProductDto> getTopProducts(@PathVariable Long shopId) {
		return reportService.getTopSellingProducts(shopId);
	}

	@GetMapping("/shop/{shopId}/top-customers")
	public List<TopCustomerDto> getTopCustomers(@PathVariable Long shopId) {
		return reportService.getTopCustomers(shopId);
	}

	@GetMapping("/shop/{shopId}/sales-summary")
	public List<SalesSummaryDto> getSalesSummary(@PathVariable Long shopId,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
			@RequestParam(defaultValue = "DAILY") String period // DAILY, WEEKLY, MONTHLY
	) {
		return reportService.getSalesSummary(shopId, from, to, period);
	}

	
	@GetMapping("/shop/{shopId}/discount-summary")
	public List<DiscountSummaryDto> getDiscountSummary(@PathVariable Long shopId,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
		return reportService.getDiscountSummary(shopId, from, to);
	}

	@GetMapping("/shop/{shopId}/inventory-movement")
	public List<InventoryMovementDto> getInventoryMovement(@PathVariable Long shopId,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
		return reportService.getInventoryMovement(shopId, from, to);
	}

	@GetMapping("/shop/{shopId}/time-insights")
	public TimeInsightDto getTimeInsights(@PathVariable Long shopId,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
		return reportService.getTimeInsights(shopId, from, to);
	}
	
	@GetMapping("/shop/{shopId}/dead-stock")
	public List<Product> getDeadStock(
	    @PathVariable Long shopId,
	    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
	    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {

	    return reportService.getDeadStockProducts(shopId, from, to);
	}

	@GetMapping("/profit")
	public ResponseEntity<ProfitSummaryDto> getProfitSummary(
	        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
	        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {

	    ProfitSummaryDto summary = reportService.getProfitBetweenDates(from, to);
	    return ResponseEntity.ok(summary);
	}



}
