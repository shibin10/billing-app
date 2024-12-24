package com.shiel.campaignapi.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PaymentNotificationDto {
	  private String bookingId;
	    private String transactionId;
	    private String status;  
	    private String paymentMethod; 
	    private BigDecimal amount;
	    private String currency; 
	    private LocalDateTime paymentTime;
	    private String customerName;
	    private String customerEmail;
	    private String customerPhone;


	    
	public String getCurrency() {
			return currency;
		}

		public void setCurrency(String currency) {
			this.currency = currency;
		}

		public LocalDateTime getPaymentTime() {
			return paymentTime;
		}

		public void setPaymentTime(LocalDateTime paymentTime) {
			this.paymentTime = paymentTime;
		}

	public String getBookingId() {
		return bookingId;
	}

	public void setBookingId(String bookingId) {
		this.bookingId = bookingId;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	public String getCustomerPhone() {
		return customerPhone;
	}

	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}

	public PaymentNotificationDto() {

	}

}
