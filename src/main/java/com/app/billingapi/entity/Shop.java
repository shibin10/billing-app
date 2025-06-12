
package com.app.billingapi.entity;

import java.io.Serializable;
import java.util.Date;

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

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "place", nullable = false)
	private String place;

	@Column(name = "status", nullable = true)
	private ShopStatus status;

	@Column(name = "map", nullable = true)
	private String map;

	@OneToOne
	@JoinColumn(name = "ownerid", referencedColumnName = "userid", nullable = true)
	private User ownerId;

	@ManyToOne
	@JoinColumn(name = "planid", referencedColumnName = "planid", nullable = true)
	private SubscriptionPlan subscriptionPlanId;

	@OneToOne(mappedBy = "shop", cascade = CascadeType.ALL)
	private ShopSubscription shopSubscription;

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

	public User getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(User ownerId) {
		this.ownerId = ownerId;
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

	public Shop(Long shopId, String name, String place, ShopStatus status, String map, User ownerId,
			SubscriptionPlan subscriptionPlan, ShopSubscription subscriptionPlanId, Date createdAt, Date updatedAt) {
		super();
		this.shopId = shopId;
		this.name = name;
		this.place = place;
		this.status = status;
		this.map = map;
		this.ownerId = ownerId;
		this.shopSubscription = shopSubscription;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}



}
