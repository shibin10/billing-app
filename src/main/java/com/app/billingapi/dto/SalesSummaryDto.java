package com.app.billingapi.dto;

import java.math.BigDecimal;

public class SalesSummaryDto {
    private String period; // Date, Week, or Month label
    private BigDecimal totalSales;

    public SalesSummaryDto(String period, BigDecimal totalSales) {
        this.period = period;
        this.totalSales = totalSales;
    }

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public BigDecimal getTotalSales() {
		return totalSales;
	}

	public void setTotalSales(BigDecimal totalSales) {
		this.totalSales = totalSales;
	}

   
}

