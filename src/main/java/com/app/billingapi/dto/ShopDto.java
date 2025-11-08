package com.app.billingapi.dto;

import java.io.Serializable;

import com.app.billingapi.enums.ShopStatus;

public class ShopDto implements Serializable {
	static final long serialVersionUID = 1L;
	private Long shopId;
	private String name;
	private String description;
	private String address;
	private String place;
	private ShopStatus status;
	private String map;
	private String gstNo;
	private String phone;
	private String logo;
	private SubscriptionPlanDto subscriptionPlan;
	private SignupUserDto owner;
	private ShopSubscriptionDto shopSubscription;
	private Long subscriptionPlanId;
	private Long ownerId;

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
	

	public String getGstNo() {
		return gstNo;
	}

	public void setGstNo(String gstNo) {
		this.gstNo = gstNo;
	}

	

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
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
