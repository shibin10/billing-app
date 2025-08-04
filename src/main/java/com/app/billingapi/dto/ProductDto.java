package com.app.billingapi.dto;

import java.math.BigDecimal;
import java.util.Date;


public class ProductDto {

	private Long productId;
	private String productNumber;
	private Long hsn;
	private String name;
	private String description;
	private Integer quantity;
	private BigDecimal purchasePrice;
	private BigDecimal wholesaleRate;
	private BigDecimal retailRate;
	private BigDecimal taxRate;
	private BigDecimal CGST;
	private BigDecimal SGST;
	private String category;
	private String imageUrl;
	private Date expiry;
	private String barcode;
	private Long shopId;
	private ShopDto shop;
	private Date createdAt;
	private Date updatedAt;

	public ProductDto() {
	}

	public ProductDto(Long productId, String productNumber, Long hsn, String name, String description, Integer quantity,
			BigDecimal purchasePrice, BigDecimal wholesaleRate, BigDecimal retailRate, BigDecimal taxRate,
			BigDecimal CGST, BigDecimal SGST, String category, String imageUrl, Date expiry, String barcode,
			Long shopId, ShopDto shop, Date createdAt, Date updatedAt) {
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
		this.CGST = CGST;
		this.SGST = SGST;
		this.category = category;
		this.imageUrl = imageUrl;
		this.expiry = expiry;
		this.barcode = barcode;
		this.shopId = shopId;
		this.shop = shop;
		this.createdAt = createdAt;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public Long getHsn() {
		return hsn;
	}

	public void setHsn(Long hsn) {
		this.hsn = hsn;
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

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public ShopDto getShop() {
		return shop;
	}

	public void setShop(ShopDto shop) {
		this.shop = shop;
	}

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
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

	public BigDecimal getCGST() {
		return CGST;
	}

	public void setCGST(BigDecimal CGST) {
		this.CGST = CGST;
	}

	public BigDecimal getSGST() {
		return SGST;
	}

	public void setSGST(BigDecimal SGST) {
		this.SGST = SGST;
	}

	

	

}
