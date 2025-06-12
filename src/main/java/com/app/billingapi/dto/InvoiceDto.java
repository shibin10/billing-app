package com.app.billingapi.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.app.billingapi.enums.PaymentMode;
import com.app.billingapi.enums.PaymentStatus;

public class InvoiceDto {

    private Long invoiceId;
    private Long customerId;
    private Long shopId;
    private Long salesId;
    private Long discountId;
    private Long userId; // Logged-in user who generated the invoice
    private BigDecimal totalAmount;
    private BigDecimal tax;
    private Date dueDate;
    private PaymentStatus paymentStatus;
    private PaymentMode paymentMode;
    private String remark;
    private ShopDto shop;

    public InvoiceDto() {}

    public InvoiceDto(Long invoiceId, Long customerId, Long shopId, Long salesId, Long discountId, 
                      Long userId, BigDecimal totalAmount, BigDecimal tax, Date dueDate, 
                      PaymentStatus paymentStatus, PaymentMode paymentMode, String remark) {
        this.invoiceId = invoiceId;
        this.customerId = customerId;
        this.shopId = shopId;
        this.salesId = salesId;
        this.discountId = discountId;
        this.userId = userId;
        this.totalAmount = totalAmount;
        this.tax = tax;
        this.dueDate = dueDate;
        this.paymentStatus = paymentStatus;
        this.paymentMode = paymentMode;
        this.remark = remark;
    }

    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Long getSalesId() {
        return salesId;
    }

    public void setSalesId(Long salesId) {
        this.salesId = salesId;
    }

    public Long getDiscountId() {
        return discountId;
    }

    public void setDiscountId(Long discountId) {
        this.discountId = discountId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
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

	public ShopDto getShop() {
		return shop;
	}

	public void setShop(ShopDto shop) {
		this.shop = shop;
	}
    
    
}

