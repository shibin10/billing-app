
package com.app.billingapi.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.app.billingapi.enums.ShopStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "shop")
public class Shop implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "shopid", nullable = false)
	private Long shopId;	

	@Column(name = "address", nullable = false)
	private String address;
	
	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "place", nullable = false)
	private String place;
	
	@Column(name = "gstno", nullable = false)
	private String gstNo;

	@Column(name = "status", nullable = true)
	private ShopStatus status;

	@Column(name = "map", nullable = true)
	private String map;
	
	@Column(name = "phone", nullable = true)
	private String phone;
	
	@Column(name = "logo", nullable = true)
	private String logo;

	@ManyToOne
	@JoinColumn(name = "ownerid", referencedColumnName = "userid", nullable = true)
	private User owner;

	@ManyToOne
	@JoinColumn(name = "planid", referencedColumnName = "planid", nullable = true)
	private SubscriptionPlan subscriptionPlanId;

	@OneToOne(mappedBy = "shop", cascade = CascadeType.ALL)
	private ShopSubscription shopSubscription;

	@ManyToMany(mappedBy = "shops")
	private List<User> users = new ArrayList<>();

	
	@CreationTimestamp
	@Column(updatable = false, name = "createdat")
	private Date createdAt;

	@UpdateTimestamp
	@Column(name = "updatedat")
	private Date updatedAt;

	public Shop() {
	}

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

	
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
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

	

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public SubscriptionPlan getSubscriptionPlanId() {
		return subscriptionPlanId;
	}

	public void setSubscriptionPlanId(SubscriptionPlan subscriptionPlanId) {
		this.subscriptionPlanId = subscriptionPlanId;
	}

	public ShopSubscription getShopSubscription() {
		return shopSubscription;
	}

	public void setShopSubscription(ShopSubscription shopSubscription) {
		this.shopSubscription = shopSubscription;
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

	public Shop(Long shopId, String name, String place, String gstNo, ShopStatus status, String map, String phone,
			String logo, User owner, SubscriptionPlan subscriptionPlanId, ShopSubscription shopSubscription,
			Date createdAt, Date updatedAt) {
		super();
		this.shopId = shopId;
		this.name = name;
		this.place = place;
		this.gstNo = gstNo;
		this.status = status;
		this.map = map;
		this.phone = phone;
		this.logo = logo;
		this.owner= owner;
		this.subscriptionPlanId = subscriptionPlanId;
		this.shopSubscription = shopSubscription;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	





}
