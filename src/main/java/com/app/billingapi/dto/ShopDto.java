package com.app.billingapi.dto;

import java.io.Serializable;

import com.app.billingapi.enums.ShopStatus;

public class ShopDto implements Serializable {
	static final long serialVersionUID = 1L;
	private Long shopId;
	private String name;
	private String place;
	private ShopStatus status;
	private String map;
	
	 private Long ownerId; 
	    private Long subscriptionPlanId;
	
	private SignupUserDto owner;
	private SubscriptionPlanDto subscriptionPlan;
	 private ShopSubscriptionDto shopSubscription;

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public ShopStatus getStatus() {
		return status;
	}

	public void setStatus(ShopStatus status) {
		this.status = status;
	}

	public String getMap() {
		return map;
	}

	public void setMap(String map) {
		this.map = map;
	}



	public Long getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(Long ownerId) {
		this.ownerId = ownerId;
	}

	public Long getSubscriptionPlanId() {
		return subscriptionPlanId;
	}

	public void setSubscriptionPlanId(Long subscriptionPlanId) {
		this.subscriptionPlanId = subscriptionPlanId;
	}

	public SubscriptionPlanDto getSubscriptionPlan() {
		return subscriptionPlan;
	}

	public void setSubscriptionPlan(SubscriptionPlanDto subscriptionPlan) {
		this.subscriptionPlan = subscriptionPlan;
	}

	public ShopSubscriptionDto getShopSubscription() {
		return shopSubscription;
	}

	public void setShopSubscription(ShopSubscriptionDto shopSubscription) {
		this.shopSubscription = shopSubscription;
	}

	public SignupUserDto getOwner() {
		return owner;
	}

	public void setOwner(SignupUserDto owner) {
		this.owner = owner;
	}

	public ShopDto() {
	}

}
