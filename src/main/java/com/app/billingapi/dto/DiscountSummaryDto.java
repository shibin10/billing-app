package com.app.billingapi.dto;

import java.math.BigDecimal;

public class DiscountSummaryDto {
    private String productName;
    private BigDecimal discount;

    public DiscountSummaryDto(String productName, BigDecimal discount) {
        this.productName = productName;
        this.discount = discount;
    }

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public BigDecimal getDiscount() {
		return discount;
	}

	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}

   
}

