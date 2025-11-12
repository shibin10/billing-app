package com.app.billingapi.entity;

import java.math.BigDecimal;
import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "purchase_items")
public class PurchaseItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "purchaseitemid", nullable = false)
	private Long purchaseItemId;

	@Column(name = "purchasenumber", nullable = true)
	private String purchaseNumber;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "purchase_id", nullable = false)
	private Purchase purchase;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "product_id")
	private Product product;

	@Column(name = "quantity", nullable = false)
	private Integer quantity;

	@Column(name = "purchaseprice", nullable = false)
	private BigDecimal purchasePrice;

	@Column(name = "cgst", nullable = true, precision = 5, scale = 2)
	private BigDecimal cgst;

	@Column(name = "sgst", nullable = true, precision = 5, scale = 2)
	private BigDecimal sgst;

	@Column(name = "taxrate", nullable = true)
	private BigDecimal taxRate;

	@Column(name = "totalamount", nullable = false)
	private BigDecimal totalAmount;

	@CreationTimestamp
	@Column(updatable = false, name = "createdat")
	private Date createdAt;

	@UpdateTimestamp
	@Column(name = "updatedat")
	private Date updatedAt;

	public Long getPurchaseItemId() {
		return purchaseItemId;
	}

	public void setPurchaseItemId(Long purchaseItemId) {
		this.purchaseItemId = purchaseItemId;
	}

	
	
	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(BigDecimal taxRate) {
		this.taxRate = taxRate;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Purchase getPurchase() {
		return purchase;
	}

	public void setPurchase(Purchase purchase) {
		this.purchase = purchase;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public BigDecimal getCgst() {
		return cgst;
	}

	public void setCgst(BigDecimal cgst) {
		this.cgst = cgst;
	}

	public BigDecimal getSgst() {
		return sgst;
	}

	public void setSgst(BigDecimal sgst) {
		this.sgst = sgst;
	}

	public String getPurchaseNumber() {
		return purchaseNumber;
	}

	public void setPurchaseNumber(String purchaseNumber) {
		this.purchaseNumber = purchaseNumber;
	}

	public BigDecimal getPurchasePrice() {
		return purchasePrice;
	}

	public void setPurchasePrice(BigDecimal purchasePrice) {
		this.purchasePrice = purchasePrice;
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

	
	
	public PurchaseItem() {
			
	}

	public PurchaseItem(Long purchaseItemId, String purchaseNumber, Purchase purchase, Product product,
			Integer quantity, BigDecimal purchasePrice, BigDecimal cgst, BigDecimal sgst, BigDecimal taxRate,
			BigDecimal totalAmount, Date createdAt, Date updatedAt) {
		super();
		this.purchaseItemId = purchaseItemId;
		this.purchaseNumber = purchaseNumber;
		this.purchase = purchase;
		this.product = product;
		this.quantity = quantity;
		this.purchasePrice = purchasePrice;
		this.cgst = cgst;
		this.sgst = sgst;
		this.taxRate = taxRate;
		this.totalAmount = totalAmount;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	
}
