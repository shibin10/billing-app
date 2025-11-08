package com.app.billingapi.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TopProductDto {

	private String productName;
    private BigDecimal quantity;
    private BigDecimal subTotal;  
    private BigDecimal tax;
    private BigDecimal discount;
    private BigDecimal finalAmount;
    private LocalDate invoiceDate; 

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}



	public LocalDate getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(LocalDate invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(BigDecimal subTotal) {
		this.subTotal = subTotal;
	}

	public BigDecimal getTax() {
		return tax;
	}

	public void setTax(BigDecimal tax) {
		this.tax = tax;
	}

	public BigDecimal getDiscount() {
		return discount;
	}

	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}

	public BigDecimal getFinalAmount() {
		return finalAmount;
	}

	public void setFinalAmount(BigDecimal finalAmount) {
		this.finalAmount = finalAmount;
	}

	public TopProductDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TopProductDto(String productName, BigDecimal quantity, BigDecimal subTotal, BigDecimal tax,
			BigDecimal discount, BigDecimal finalAmount, LocalDate invoiceDate) {
		super();
		this.productName = productName;
		this.quantity = quantity;
		this.subTotal = subTotal;
		this.tax = tax;
		this.discount = discount;
		this.finalAmount = finalAmount;
		this.invoiceDate = invoiceDate;
	}




}