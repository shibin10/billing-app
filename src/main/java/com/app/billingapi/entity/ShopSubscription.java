package com.app.billingapi.entity;

import java.io.Serializable;
import java.time.LocalDate;

import com.app.billingapi.enums.PaymentStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "shop_subscription")
public class ShopSubscription implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "subscriptionid", nullable = false)
	private Long subscriptionId;

	@OneToOne
	@JoinColumn(name = "shopid", referencedColumnName = "shopid", nullable = false, unique = true)
	private Shop shop;


	@ManyToOne
	@JoinColumn(name = "planid", referencedColumnName = "planid", nullable = false)
	private SubscriptionPlan subscriptionPlan;

	@Column(name = "startdate", nullable = false)
	private LocalDate startDate;

	@Column(name = "enddate", nullable = true) // NULL if lifetime plan
	private LocalDate endDate;

	@Enumerated(EnumType.STRING)
	@Column(name = "paymentstatus", nullable = false)
	private PaymentStatus paymentStatus; // PAID, UNPAID, EXPIRED

	@Column(name = "renewalreminder", nullable = false)
	private Boolean renewalReminder = true;

	public Long getSubscriptionId() {
		return subscriptionId;
	}

	public void setSubscriptionId(Long subscriptionId) {
		this.subscriptionId = subscriptionId;
	}

	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

	public SubscriptionPlan getSubscriptionPlan() {
		return subscriptionPlan;
	}

	public void setSubscriptionPlan(SubscriptionPlan subscriptionPlan) {
		this.subscriptionPlan = subscriptionPlan;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public PaymentStatus getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(PaymentStatus paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public Boolean getRenewalReminder() {
		return renewalReminder;
	}

	public void setRenewalReminder(Boolean renewalReminder) {
		this.renewalReminder = renewalReminder;
	}

	public ShopSubscription() {

	}

}
