package com.app.billingapi.dto;

import java.util.List;

import com.app.billingapi.entity.Role;
import com.app.billingapi.entity.Shop;
import com.app.billingapi.enums.UserStatus;

public class SignupUserDto {
	private Long userId;
	private String fullName;
	private String place;
	private Integer countryId;
	private CountryDto country;
	private String phone;
	private String email;
	private String password;
	private SignupUserDto referredBy;
	private UserStatus status;
	private ShopDto shop;
	private List<Role> roles;
	private List<Shop> shops;
	private List<Long> shopIds; 
	private Long shopId;
	private Long roleId;
	private Long referredByUserId;

	public String getEmail() {
		return email;
	}

	public SignupUserDto setEmail(String email) {
		this.email = email;
		return this;
	}

	public String getPassword() {
		return password;
	}

	public SignupUserDto setPassword(String password) {
		this.password = password;
		return this;
	}

	public String getFullName() {
		return fullName;
	}

	public SignupUserDto setFullName(String fullName) {
		this.fullName = fullName;
		return this;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public Integer getCountryId() {
		return countryId;
	}

	public void setCountryId(Integer countryId) {
		this.countryId = countryId;
	}

	public CountryDto getCountry() {
		return country;
	}

	public void setCountry(CountryDto country) {
		this.country = country;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public SignupUserDto getReferredBy() {
		return referredBy;
	}

	public void setReferredBy(SignupUserDto referredBy) {
		this.referredBy = referredBy;
	}

	public Long getReferredByUserId() {
		return referredByUserId;
	}

	public void setReferredByUserId(Long referredByUserId) {
		this.referredByUserId = referredByUserId;
	}

	
	public ShopDto getShop() {
		return shop;
	}

	public void setShop(ShopDto shop) {
		this.shop = shop;
	}

	public List<Shop> getShops() {
		return shops;
	}

	public List<Long> getShopIds() {
		return shopIds;
	}

	public void setShopIds(List<Long> shopIds) {
		this.shopIds = shopIds;
	}

	public void setShops(List<Shop> shops) {
		this.shops = shops;
	}

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public UserStatus getStatus() {
		return status;
	}

	public void setStatus(UserStatus status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "SignupUserDto [userId=" + userId + ", fullName=" + fullName + ", place=" + place + ", countryId="
				+ countryId + ", country=" + country + ", phone=" + phone + ", email=" + email + ", password="
				+ password + ", roles=" + roles + ", roleId=" + roleId + ", status=" + status + "]";
	}

}
