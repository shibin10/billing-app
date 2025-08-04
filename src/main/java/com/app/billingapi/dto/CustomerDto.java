package com.app.billingapi.dto;

import java.math.BigDecimal;

import com.app.billingapi.enums.CustomerType;

public class CustomerDto {
	private Long customerId;
	private String place;
	private String name;
	private String phone;
	private BigDecimal totalSpend;
	private SignupUserDto referredby;
	 private CustomerType customerType;
	 private Integer loyaltyPoints;
	private ShopDto shop;
	private long shopId;

	 //private List<InvoiceDto> invoices;

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public ShopDto getShop() {
		return shop;
	}

	public void setShop(ShopDto shop) {
		this.shop = shop;
	}

	public long getShopId() {
		return shopId;
	}

	public void setShopId(long shopId) {
		this.shopId = shopId;
	}
	


	public CustomerType getCustomerType() {
		return customerType;
	}

	public void setCustomerType(CustomerType customerType) {
		this.customerType = customerType;
	}

	public Integer getLoyaltyPoints() {
		return loyaltyPoints;
	}

	public void setLoyaltyPoints(Integer loyaltyPoints) {
		this.loyaltyPoints = loyaltyPoints;
	}

	public BigDecimal getTotalSpend() {
		return totalSpend;
	}

	public void setTotalSpend(BigDecimal totalSpend) {
		this.totalSpend = totalSpend;
	}

	public SignupUserDto getReferredby() {
		return referredby;
	}

	public void setReferredby(SignupUserDto referredby) {
		this.referredby = referredby;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public CustomerDto() {

	}

}
