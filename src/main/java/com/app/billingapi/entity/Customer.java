package com.app.billingapi.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "customer")
public class Customer implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "customerid", nullable = false)
	private Long customerId;

	@Column(name = "name", nullable = true)
	private String name;

	@Column(name = "place", nullable = false)
	private String place;

	@Column(name = "phone", nullable = false)
	private Integer phone;
	
	@Column(name="totalspend", nullable=true)
	private BigDecimal  totalSpend;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "shopid", referencedColumnName = "shopid")
	private Shop shopId;
	
	@ManyToOne
    @JoinColumn(name = "referredby", referencedColumnName = "userid", nullable = true)
    private User referredBy;
	
	@OneToMany(mappedBy = "customerId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Invoice> invoices;

	@CreationTimestamp
	@Column(updatable = false, name = "createdat")
	private Date createdAt;

	@UpdateTimestamp
	@Column(name = "updatedat")
	private Date updatedAt;

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

	public Shop getShopId() {
		return shopId;
	}

	public void setShopId(Shop shopId) {
		this.shopId = shopId;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
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

	public Integer getPhone() {
		return phone;
	}

	public void setPhone(Integer phone) {
		this.phone = phone;
	}
	

	public List<Invoice> getInvoices() {
		return invoices;
	}

	public void setInvoices(List<Invoice> invoices) {
		this.invoices = invoices;
	}

	public Customer() {

	}

	public Customer(Long customerId, String name, String place, Integer phone, Shop shopId, List<Invoice> invoices,
			Date createdAt, Date updatedAt) {
		super();
		this.customerId = customerId;
		this.name = name;
		this.place = place;
		this.phone = phone;
		this.shopId = shopId;
		this.invoices = invoices;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	

}
