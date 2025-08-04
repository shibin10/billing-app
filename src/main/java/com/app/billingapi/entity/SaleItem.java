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

@Table(name = "sale_item")
@Entity
public class SaleItem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "saleitemid")
	private Long saleItemId;

	@ManyToOne
	@JoinColumn(name = "saleid", referencedColumnName = "saleid")
	private Sale saleId;

	@ManyToOne
	@JoinColumn(name = "productid", referencedColumnName = "productid")
	private Product product;

	@Column(name = "quantity")
	private BigDecimal quantity;

	@Column(name = "price",nullable = true)
	private BigDecimal price;

	@Column(name = "tax", nullable = true)
	private BigDecimal tax;

	@Column(name = "total")
	private BigDecimal total;
	
	@Column(name = "discount")
	private BigDecimal discount;

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
		return saleItemId;
	}

	public void setInvoiceItemId(Long saleItemId) {
		this.saleItemId = saleItemId;
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

	
	public Long getSaleItemId() {
		return saleItemId;
	}

	public void setSaleItemId(Long saleItemId) {
		this.saleItemId = saleItemId;
	}

	public BigDecimal getDiscount() {
		return discount;
	}

	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}

	public SaleItem() {

	}

	public Sale getSaleId() {
		return saleId;
	}

	public void setSaleId(Sale saleId) {
		this.saleId = saleId;
	}

	public SaleItem(Long saleItemId, Sale saleId, Product product, BigDecimal quantity, BigDecimal price,
			BigDecimal tax, BigDecimal total, Date createdAt, Date updatedAt) {
		super();
		this.saleItemId = saleItemId;
		this.saleId = saleId;
		this.product = product;
		this.quantity = quantity;
		this.price = price;
		this.tax = tax;
		this.total = total;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	

}
