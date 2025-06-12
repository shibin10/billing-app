package com.app.billingapi.dto;

import java.math.BigDecimal;
import java.util.Date;


import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Pattern;

public class ProductDto {
	private Long productId;
	private String productNumber;
	private Long hsn;
	private String name;
	private String description;
	@Pattern(regexp = "\\d+", message = "Quantity must be a number")
	private Integer quantity;
	@DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
	private BigDecimal ourPrice;
	@DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
	private BigDecimal wholesaleRate;
	@DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
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

	public BigDecimal getOurPrice() {
		return ourPrice;
	}

	public void setOurPrice(BigDecimal ourPrice) {
		this.ourPrice = ourPrice;
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

	public void setCGST(BigDecimal cGST) {
		CGST = cGST;
	}

	public BigDecimal getSGST() {
		return SGST;
	}

	public void setSGST(BigDecimal sGST) {
		SGST = sGST;
	}

	
	

}
