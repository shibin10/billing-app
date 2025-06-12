package com.app.billingapi.entity;

import java.math.BigDecimal;
import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Table(name = "invoice_item")
@Entity
public class InvoiceItem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "invoiceitemid")
	private Long invoiceItemId;

	@ManyToOne
	@JoinColumn(name = "invoiceid", referencedColumnName = "invoiceid")
	private Invoice invoice;

	@ManyToOne
	@JoinColumn(name = "productid", referencedColumnName = "productid")
	private Product product;

	@Column(name = "quantity")
	private BigDecimal quantity;

	@Column(name = "price")
	private BigDecimal price;

	@Column(name = "tax", nullable = true)
	private BigDecimal tax;

	@Column(name = "total")
	private BigDecimal total;

	@CreationTimestamp
	@Column(updatable = false, name = "createdat")
	private Date createdAt;

	@UpdateTimestamp
	@Column(name = "updatedat")
	private Date updatedAt;

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}


	public Invoice getInvoice() {
		return invoice;
	}

	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
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

	public Long getInvoiceItemId() {
		return invoiceItemId;
	}

	public void setInvoiceItemId(Long invoiceItemId) {
		this.invoiceItemId = invoiceItemId;
	}

	public Invoice getInvoiceId() {
		return invoice;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public InvoiceItem() {

	}

	public InvoiceItem(Long invoiceItemId, Invoice invoice, Product product, BigDecimal quantity, BigDecimal price,
			BigDecimal tax, BigDecimal total, Date createdAt, Date updatedAt) {
		super();
		this.invoiceItemId = invoiceItemId;
		this.invoice = invoice;
		this.product = product;
		this.quantity = quantity;
		this.price = price;
		this.tax = tax;
		this.total = total;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

}
