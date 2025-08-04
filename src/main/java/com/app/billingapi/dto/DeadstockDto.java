package com.app.billingapi.dto;

import java.math.BigDecimal;

public class DeadstockDto {
    private String productName;
    private BigDecimal quantity;

    public DeadstockDto(String productName, BigDecimal quantity) {
        this.productName = productName;
        this.quantity = quantity;
    }

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}
  
}

