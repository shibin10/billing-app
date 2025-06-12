package com.app.billingapi.dto;

import java.math.BigDecimal;

public class InvoiceItemDto {

	private Long invoiceItemId;
	private Long invoiceId;
	private Long productId;
	private BigDecimal quantity;
	private BigDecimal price;
	private BigDecimal tax;
	private BigDecimal total;
	private InvoiceDto invoice;

	public Long getInvoiceItemId() {
		return invoiceItemId;
	}

	public void setInvoiceItemId(Long invoiceItemId) {
		this.invoiceItemId = invoiceItemId;
	}

	public Long getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(Long invoiceId) {
		this.invoiceId = invoiceId;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getTax() {
		return tax;
	}

	public void setTax(BigDecimal tax) {
		this.tax = tax;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public InvoiceDto getInvoice() {
		return invoice;
	}

	public void setInvoice(InvoiceDto invoice) {
		this.invoice = invoice;
	}

}
