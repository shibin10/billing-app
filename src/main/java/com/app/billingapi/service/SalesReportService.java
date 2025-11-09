package com.app.billingapi.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.billingapi.dto.ProductSalesReportDto;
import com.app.billingapi.exception.SalesReportNotFoundException;
import com.app.billingapi.repository.SaleItemRepository;

@Service
public class SalesReportService {

	@Autowired
	private SaleItemRepository saleItemRepository;
	
	
	public List<ProductSalesReportDto> getAllSalesReportsByDate(LocalDate fromDate, LocalDate toDate) {
	    List<Object[]> results = saleItemRepository.getSalesReportForAllProductsByDate(
	            fromDate.toString(), toDate.toString()
	    );

	    if (results.isEmpty()) {
	        throw new RuntimeException("No sales reports found for given date range.");
	    }

	    return results.stream().map(row -> new ProductSalesReportDto(
	            row[0] != null ? row[0].toString() : null,
	            row[1] != null ? row[1].toString() : null,
	            row[2] != null ? new BigDecimal(row[2].toString()) : BigDecimal.ZERO,
	            row[3] != null ? new BigDecimal(row[3].toString()) : BigDecimal.ZERO,
	            row[4] != null ? new BigDecimal(row[4].toString()) : BigDecimal.ZERO,
	            row[5] != null ? new BigDecimal(row[5].toString()) : BigDecimal.ZERO,
	            row[6] != null ? new BigDecimal(row[6].toString()) : BigDecimal.ZERO
	    )).collect(Collectors.toList());
	}

	public ProductSalesReportDto getSalesReportByHsnAndDate(String hsn, LocalDate fromDate, LocalDate toDate) {
	    List<Object[]> rows = saleItemRepository.getSalesReportByHsnAndDate(
	            hsn, fromDate.toString(), toDate.toString()
	    );

	    if (rows.isEmpty()) {
	        throw new SalesReportNotFoundException("error", "No sales report found for HSN: " + hsn, 900);
	    }

	    Object[] row = rows.get(0);
	    return new ProductSalesReportDto(
	            row[0] != null ? row[0].toString() : null,
	            row[1] != null ? row[1].toString() : null,
	            row[2] != null ? new BigDecimal(row[2].toString()) : BigDecimal.ZERO,
	            row[3] != null ? new BigDecimal(row[3].toString()) : BigDecimal.ZERO,
	            row[4] != null ? new BigDecimal(row[4].toString()) : BigDecimal.ZERO,
	            row[5] != null ? new BigDecimal(row[5].toString()) : BigDecimal.ZERO,
	            row[6] != null ? new BigDecimal(row[6].toString()) : BigDecimal.ZERO
	    );
	}
	
}
