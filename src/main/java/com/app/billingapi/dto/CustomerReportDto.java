package com.app.billingapi.dto;

import java.math.BigDecimal;
import java.util.List;

public class CustomerReportDto {

    // 1. Customer Profile
    private Long customerId;
    private String customerName;
    private String phone;
    private String place;
    private String customerType;

    // 2. Sales Summary
    private BigDecimal totalPurchases;
    private Integer totalBills;
    private BigDecimal averageBillValue;
    private BigDecimal biggestBill;
    private BigDecimal totalDiscount;

    // 3. Payment & Dues
    private BigDecimal totalPaid;
    private BigDecimal pendingBalance;
    private BigDecimal advancePaid;
    private Integer overdueInvoices;

    // 4. Top Products
    private List<TopProductDto> topProducts;

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public BigDecimal getTotalPurchases() {
		return totalPurchases;
	}

	public void setTotalPurchases(BigDecimal totalPurchases) {
		this.totalPurchases = totalPurchases;
	}

	public Integer getTotalBills() {
		return totalBills;
	}

	public void setTotalBills(Integer totalBills) {
		this.totalBills = totalBills;
	}

	public BigDecimal getAverageBillValue() {
		return averageBillValue;
	}

	public void setAverageBillValue(BigDecimal averageBillValue) {
		this.averageBillValue = averageBillValue;
	}

	public BigDecimal getBiggestBill() {
		return biggestBill;
	}

	public void setBiggestBill(BigDecimal biggestBill) {
		this.biggestBill = biggestBill;
	}

	public BigDecimal getTotalDiscount() {
		return totalDiscount;
	}

	public void setTotalDiscount(BigDecimal totalDiscount) {
		this.totalDiscount = totalDiscount;
	}

	public BigDecimal getTotalPaid() {
		return totalPaid;
	}

	public void setTotalPaid(BigDecimal totalPaid) {
		this.totalPaid = totalPaid;
	}

	public BigDecimal getPendingBalance() {
		return pendingBalance;
	}

	public void setPendingBalance(BigDecimal pendingBalance) {
		this.pendingBalance = pendingBalance;
	}

	public BigDecimal getAdvancePaid() {
		return advancePaid;
	}

	public void setAdvancePaid(BigDecimal advancePaid) {
		this.advancePaid = advancePaid;
	}

	public Integer getOverdueInvoices() {
		return overdueInvoices;
	}

	public void setOverdueInvoices(Integer overdueInvoices) {
		this.overdueInvoices = overdueInvoices;
	}

	public List<TopProductDto> getTopProducts() {
		return topProducts;
	}

	public void setTopProducts(List<TopProductDto> topProducts) {
		this.topProducts = topProducts;
	}
    
    
}

   

