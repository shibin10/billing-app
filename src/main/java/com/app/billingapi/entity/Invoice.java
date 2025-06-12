package com.app.billingapi.entity;

import java.math.BigDecimal;
import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.app.billingapi.enums.PaymentMode;
import com.app.billingapi.enums.PaymentStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Table(name = "invoice")
@Entity
public class Invoice {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "invoiceid")
	private Long invoiceId;

	@ManyToOne
	@JoinColumn(name = "customerid", referencedColumnName = "customerid")
	private Customer customerId;

	@ManyToOne
	@JoinColumn(name = "shopid", referencedColumnName = "shopid")
	private Shop shopId;
	
	
	@ManyToOne
	@JoinColumn(name = "saleid", referencedColumnName = "saleid")
	private Sale salesId;

	@ManyToOne
	@JoinColumn(name = "discountid", referencedColumnName = "discountid")
	private Discount discountId;
	
	@ManyToOne
	@JoinColumn(name = "loggedinuserid", referencedColumnName = "userid")
	private User userId;


	@Column(name = "totalamount")
	private BigDecimal totalAmount;
	
	@Column(name = "tax")
	private BigDecimal tax;
	
	@Column(name = "duedate")
	private Date dueDate;

	@Column(name = "paymentstatus")
	private PaymentStatus paymentStatus;

	@Column(name = "paymentmode")
	private PaymentMode paymentMode;

	@Column(name = "remark")
	private String remark;
	
	@CreationTimestamp
	@Column(updatable = false, name = "createdat")
	private Date createdAt;

	@UpdateTimestamp
	@Column(name = "updatedat")
	private Date updatedAt;

	public Long getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(Long invoiceId) {
		this.invoiceId = invoiceId;
	}

	public Customer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Customer customerId) {
		this.customerId = customerId;
	}

	public User getUserId() {
		return userId;
	}

	public void setUserId(User userId) {
		this.userId = userId;
	}

	public Shop getShopId() {
		return shopId;
	}

	public void setShopId(Shop shopId) {
		this.shopId = shopId;
	}



	public Sale getSalesId() {
		return salesId;
	}

	public void setSalesId(Sale salesId) {
		this.salesId = salesId;
	}

	public Discount getDiscountId() {
		return discountId;
	}

	public void setDiscountId(Discount discountId) {
		this.discountId = discountId;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public PaymentMode getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(PaymentMode paymentMode) {
		this.paymentMode = paymentMode;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public BigDecimal getTax() {
		return tax;
	}

	public void setTax(BigDecimal tax) {
		this.tax = tax;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public PaymentStatus getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(PaymentStatus paymentStatus) {
		this.paymentStatus = paymentStatus;
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

	public Invoice() {

	}


}
