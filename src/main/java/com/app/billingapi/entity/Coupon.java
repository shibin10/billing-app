package com.app.billingapi.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.app.billingapi.enums.CouponType;
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
@Table(name = "coupon")
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "couponid", nullable = false)
    private Long couponId;

    @Column(name = "coupon_code", unique = true, nullable = false)
    private String couponCode; 

    @Enumerated(EnumType.STRING)
    @Column(name = "coupon_type", nullable = false)
    private CouponType couponType; 

    @Column(name = "description")
    private String description; // "₹50 off on first purchase"

    @Column(name = "discount_value", nullable = false)
    private BigDecimal discountValue; // Discount amount (₹50, 10%)

    @Enumerated(EnumType.STRING)
    @Column(name = "discount_unit", nullable = false)
    private DiscountUnit discountUnit; // PERCENTAGE or FIXED_AMOUNT

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

    @Column(name = "is_used", nullable = false)
    private Boolean isUsed = false; 

    @ManyToOne
    @JoinColumn(name = "customerid", referencedColumnName = "customerid", nullable = true)
    private Customer customer; 

	@CreationTimestamp
	@Column(updatable = false, name = "createdat")
	private Date createdAt;

	@UpdateTimestamp
	@Column(name = "updatedat")
	private Date updatedAt;
}

