package com.app.billingapi.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;

import com.app.billingapi.enums.PlanType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "subscription_plan")
public class SubscriptionPlan implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "planid", nullable = false)
	private Long planId;

	@Enumerated(EnumType.STRING)
	@Column(name = "planname", nullable = false)
	private PlanType planName;

	@Column(name = "price", nullable = false)
	private BigDecimal price;

	@Column(name = "duration", nullable = false)
	private Integer duration; // Months (0 for Lifetime)

	@Column(name = "features", columnDefinition = "TEXT")
	private String features;

	@CreationTimestamp
	@Column(updatable = false, name = "createdat")
	private Date createdAt;

	
	
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

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public SubscriptionPlan() {

	}

}
