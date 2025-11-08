package com.app.billingapi.entity;

import java.io.Serializable;
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

@Table(name = "product")
@Entity
public class Product implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "productid", nullable = false)
	private Long productId;

	@Column(name = "productnumber", nullable = true)
	private String productNumber;
	
	@Column(name = "hsn", nullable = true)
	private Long hsn;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "description", nullable = false)
	private String description;

	@Column(name = "quantity", nullable = false)
	private Integer quantity;

	@Column(name = "purchaseprice", nullable = false)
	private BigDecimal purchasePrice;

	@Column(name = "wholesalerate", nullable = true)
	private BigDecimal wholesaleRate;

	@Column(name = "retailrate", nullable = true)
	private BigDecimal retailRate;

	@Column(name = "taxrate", nullable = true)
	private BigDecimal taxRate;

	@Column(name = "catogory", nullable = true)
	private String category;
	
	@Column(name = "location", nullable = true)
	private String location;

	@Column(name = "image_url", nullable = true)
	private String imageUrl;

	@Column(name = "expiry")
	private Date expiry;

	@Column(name = "barcode", nullable = true)
	private String barcode;

	@Column(name = "cgst", nullable = true, precision = 5, scale = 2)
	private BigDecimal cgst;

	@Column(name = "sgst", nullable = true, precision = 5, scale = 2)
	private BigDecimal sgst;

	@ManyToOne
	@JoinColumn(name = "shopid", referencedColumnName = "shopid")
	private Shop shopId;

	@CreationTimestamp
	@Column(updatable = false, name = "createdat")
	private Date createdAt;

	@UpdateTimestamp
	@Column(name = "updatedat")
	private Date updatedAt;

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

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getProductNumber() {
		return productNumber;
	}

	public void setProductNumber(String productNumber) {
		this.productNumber = productNumber;
	}

	public Shop getShopId() {
		return shopId;
	}

	public Product setShopId(Shop shopId) {
		this.shopId = shopId;
		return this;

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Long getHsn() {
		return hsn;
	}

	public void setHsn(Long hsn) {
		this.hsn = hsn;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	

	public BigDecimal getPurchasePrice() {
		return purchasePrice;
	}

	public void setPurchasePrice(BigDecimal purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	public BigDecimal getWholesaleRate() {
		return wholesaleRate;
	}

	public void setWholesaleRate(BigDecimal wholesaleRate) {
		this.wholesaleRate = wholesaleRate;
	}

	public BigDecimal getRetailRate() {
		return retailRate;
	}

	public void setRetailRate(BigDecimal retailRate) {
		this.retailRate = retailRate;
	}

	public BigDecimal getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(BigDecimal taxRate) {
		this.taxRate = taxRate;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Date getExpiry() {
		return expiry;
	}

	public void setExpiry(Date expiry) {
		this.expiry = expiry;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
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

	public Product() {
	}

	public Product(Long productId, String productNumber, Long hsn, String name, String description, Integer quantity,
			BigDecimal purchasePrice, BigDecimal wholesaleRate, BigDecimal retailRate, BigDecimal taxRate,
			String category, String location, String imageUrl, Date expiry, String barcode, BigDecimal cgst,
			BigDecimal sgst, Shop shopId, Date createdAt, Date updatedAt) {
		super();
		this.productId = productId;
		this.productNumber = productNumber;
		this.hsn = hsn;
		this.name = name;
		this.description = description;
		this.quantity = quantity;
		this.purchasePrice = purchasePrice;
		this.wholesaleRate = wholesaleRate;
		this.retailRate = retailRate;
		this.taxRate = taxRate;
		this.category = category;
		this.location = location;
		this.imageUrl = imageUrl;
		this.expiry = expiry;
		this.barcode = barcode;
		this.cgst = cgst;
		this.sgst = sgst;
		this.shopId = shopId;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	

}
