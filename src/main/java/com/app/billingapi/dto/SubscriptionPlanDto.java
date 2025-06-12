package com.app.billingapi.dto;

import java.math.BigDecimal;

import com.app.billingapi.enums.PlanType;

public class SubscriptionPlanDto {
	private Long planId;
	private PlanType planName;
	private BigDecimal price;
	private Integer duration;
	private String features;

	public Long getPlanId() {
		return planId;
	}

	public void setPlanId(Long planId) {
		this.planId = planId;
	}

	public PlanType getPlanName() {
		return planName;
	}

	public void setPlanName(PlanType planName) {
		this.planName = planName;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public String getFeatures() {
		return features;
	}

	public void setFeatures(String features) {
		this.features = features;
	}

	public SubscriptionPlanDto() {
		super();
	}

}
