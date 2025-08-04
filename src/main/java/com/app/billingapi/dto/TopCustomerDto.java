package com.app.billingapi.dto;

import java.math.BigDecimal;

public class TopCustomerDto {
    private String customerName;
    private BigDecimal totalSpent;

    public TopCustomerDto(String customerName, BigDecimal totalSpent) {
        this.customerName = customerName;
        this.totalSpent = totalSpent;
    }

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public BigDecimal getTotalSpent() {
		return totalSpent;
	}

	public void setTotalSpent(BigDecimal totalSpent) {
		this.totalSpent = totalSpent;
	}

  
}
