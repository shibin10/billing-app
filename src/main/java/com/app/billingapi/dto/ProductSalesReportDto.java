package com.app.billingapi.dto;

import java.math.BigDecimal;

public class ProductSalesReportDto {

	private String hsn;
	private String productName;
	private BigDecimal taxRate;
	private BigDecimal totalQuantity;
	private BigDecimal totalAmount; // qty × retailrate (without tax)
	private BigDecimal totalTax; // only tax
	private BigDecimal finalAmount; // with tax

	public ProductSalesReportDto(String hsn, String productName, BigDecimal taxRate,BigDecimal totalQuantity, BigDecimal totalAmount,
			 BigDecimal totalTax, BigDecimal finalAmount) {
		super();
		this.hsn = hsn;
		this.productName = productName;
		this.taxRate = taxRate;
		this.totalQuantity = totalQuantity;
		this.totalAmount = totalAmount;
		this.totalTax = totalTax;
		this.finalAmount = finalAmount;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public BigDecimal getFinalAmount() {
		return finalAmount;
	}

	public void setFinalAmount(BigDecimal finalAmount) {
		this.finalAmount = finalAmount;
	}

	public String getHsn() {
		return hsn;
	}

	public void setHsn(String hsn) {
		this.hsn = hsn;
	}

	public BigDecimal getTotalQuantity() {
		return totalQuantity;
	}

	public void setTotalQuantity(BigDecimal totalQuantity) {
		this.totalQuantity = totalQuantity;
	}

	public BigDecimal getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(BigDecimal taxRate) {
		this.taxRate = taxRate;
	}

	public BigDecimal getTotalTax() {
		return totalTax;
	}

	public void setTotalTax(BigDecimal totalTax) {
		this.totalTax = totalTax;
	}

	public ProductSalesReportDto() {
		super();
		// TODO Auto-generated constructor stub
	}

}
