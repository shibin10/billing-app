package com.app.billingapi.dto;

public class TopProductDto {
    private String productName;
    private Double totalQuantity;
    
    

    public String getProductName() {
		return productName;
	}



	public void setProductName(String productName) {
		this.productName = productName;
	}



	public Double getTotalQuantity() {
		return totalQuantity;
	}



	public void setTotalQuantity(Double totalQuantity) {
		this.totalQuantity = totalQuantity;
	}



	public TopProductDto(String productName, Double totalQuantity) {
        this.productName = productName;
        this.totalQuantity = totalQuantity;
    }
}