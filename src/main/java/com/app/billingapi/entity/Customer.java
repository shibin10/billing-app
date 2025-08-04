package com.app.billingapi.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.app.billingapi.enums.CustomerType;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
	private String phone;
	
	@Column(name="totalspend", nullable=true)
	private BigDecimal  totalSpend;
	
    @Enumerated(EnumType.STRING)
    @Column(name = "customertype", nullable = false)
    private CustomerType customerType;

	
    @Column(name = "loyaltypoints", nullable = true)
    private Integer loyaltyPoints = 0;

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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	

	public List<Invoice> getInvoices() {
		return invoices;
	}

	public void setInvoices(List<Invoice> invoices) {
		this.invoices = invoices;
	}
	
	

	public BigDecimal getTotalSpend() {
		return totalSpend;
	}

	public void setTotalSpend(BigDecimal totalSpend) {
		this.totalSpend = totalSpend;
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

	public User getReferredBy() {
		return referredBy;
	}

	public void setReferredBy(User referredBy) {
		this.referredBy = referredBy;
	}

	public Customer() {

	}

	public Customer(Long customerId, String name, String place, String phone, BigDecimal totalSpend,
			CustomerType customerType, Integer loyaltyPoints, Shop shopId, User referredBy, List<Invoice> invoices,
			Date createdAt, Date updatedAt) {
	
		this.customerId = customerId;
		this.name = name;
		this.place = place;
		this.phone = phone;
		this.totalSpend = totalSpend;
		this.customerType = customerType;
		this.loyaltyPoints = loyaltyPoints;
		this.shopId = shopId;
		this.referredBy = referredBy;
		this.invoices = invoices;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	
	

}
