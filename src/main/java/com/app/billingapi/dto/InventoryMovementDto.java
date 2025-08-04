package com.app.billingapi.dto;

public class InventoryMovementDto {
    private String productName;
    private Double quantityMoved;

    public InventoryMovementDto(String productName, Double quantityMoved) {
        this.productName = productName;
        this.quantityMoved = quantityMoved;
    }

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Double getQuantityMoved() {
		return quantityMoved;
	}

	public void setQuantityMoved(Double quantityMoved) {
		this.quantityMoved = quantityMoved;
	}

}
