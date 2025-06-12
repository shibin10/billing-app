package com.app.billingapi.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.app.billingapi.enums.DiscountType;
import com.app.billingapi.enums.DiscountUnit;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "discount")
public class Discount implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "discountid", nullable = false)
	private Long discountId;

	@Column(name = "discount_code", unique = true, nullable = false)
	private String discountCode; // e.g., "XMAS10", "VIP5"

	@Enumerated(EnumType.STRING)
	@Column(name = "discount_type", nullable = false)
	private DiscountType discountType; // SEASONAL, REFERRAL, LOYALTY

	@Column(name = "description")
	private String description; 

	@Column(name = "discount_value", nullable = false)
	private BigDecimal discountValue;

	@Enumerated(EnumType.STRING)
	@Column(name = "discount_unit", nullable = false)
	private DiscountUnit discountUnit; 

	@Column(name = "min_purchase_amount", nullable = true)
	private BigDecimal minPurchaseAmount; 

	@Column(name = "max_discount_amount", nullable = true)
	private BigDecimal maxDiscountAmount;

	@Column(name = "valid_from", nullable = false)
	private LocalDate validFrom; 

	@Column(name = "valid_to", nullable = false)
	private LocalDate validTo; 

	@Column(name = "is_active", nullable = false)
	private Boolean isActive = true; 

	@ManyToOne
	@JoinColumn(name = "customerid", referencedColumnName = "customerid", nullable = true)
	private Customer customer; // If assigned to a specific customer

	@Column(name = "is_used", nullable = false)
	private Boolean isUsed = false; // If a discount is one-time, track usage

	@CreationTimestamp
	@Column(updatable = false, name = "createdat")
	private Date createdAt;

	@UpdateTimestamp
	@Column(name = "updatedat")
	private Date updatedAt;

	public Long getDiscountId() {
		return discountId;
	}

	public void setDiscountId(Long discountId) {
		this.discountId = discountId;
	}

	public String getDiscountCode() {
		return discountCode;
	}

	public void setDiscountCode(String discountCode) {
		this.discountCode = discountCode;
	}

	public DiscountType getDiscountType() {
		return discountType;
	}

	public void setDiscountType(DiscountType discountType) {
		this.discountType = discountType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getDiscountValue() {
		return discountValue;
	}

	public void setDiscountValue(BigDecimal discountValue) {
		this.discountValue = discountValue;
	}

	public DiscountUnit getDiscountUnit() {
		return discountUnit;
	}

	public void setDiscountUnit(DiscountUnit discountUnit) {
		this.discountUnit = discountUnit;
	}

	public BigDecimal getMinPurchaseAmount() {
		return minPurchaseAmount;
	}

	public void setMinPurchaseAmount(BigDecimal minPurchaseAmount) {
		this.minPurchaseAmount = minPurchaseAmount;
	}

	public BigDecimal getMaxDiscountAmount() {
		return maxDiscountAmount;
	}

	public void setMaxDiscountAmount(BigDecimal maxDiscountAmount) {
		this.maxDiscountAmount = maxDiscountAmount;
	}

	public LocalDate getValidFrom() {
		return validFrom;
	}

	public void setValidFrom(LocalDate validFrom) {
		this.validFrom = validFrom;
	}

	public LocalDate getValidTo() {
		return validTo;
	}

	public void setValidTo(LocalDate validTo) {
		this.validTo = validTo;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Boolean getIsUsed() {
		return isUsed;
	}

	public void setIsUsed(Boolean isUsed) {
		this.isUsed = isUsed;
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
	
	
	
}
