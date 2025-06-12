package com.app.billingapi.dto;

import java.time.LocalDate;

import com.app.billingapi.enums.PaymentStatus;

public class ShopSubscriptionDto {

	private Long subscriptionId;
	private ShopDto shop;
	private SubscriptionPlanDto subscriptionPlan;
	private LocalDate startDate;
	private LocalDate endDate;
	private PaymentStatus paymentStatus;
	private Boolean renewalReminder;

	public Long getSubscriptionId() {
		return subscriptionId;
	}

	public void setSubscriptionId(Long subscriptionId) {
		this.subscriptionId = subscriptionId;
	}

	public ShopDto getShop() {
		return shop;
	}

	public void setShop(ShopDto shop) {
		this.shop = shop;
	}

	public SubscriptionPlanDto getSubscriptionPlan() {
		return subscriptionPlan;
	}

	public void setSubscriptionPlan(SubscriptionPlanDto subscriptionPlan) {
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
}
