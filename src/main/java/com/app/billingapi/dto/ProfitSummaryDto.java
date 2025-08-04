package com.app.billingapi.dto;

import java.math.BigDecimal;

public class ProfitSummaryDto {
	private BigDecimal totalRevenue;
	private BigDecimal totalCost;
	private BigDecimal totalDiscount;
	private BigDecimal totalProfit;

	public BigDecimal getTotalRevenue() {
		return totalRevenue;
	}

	public void setTotalRevenue(BigDecimal totalRevenue) {
		this.totalRevenue = totalRevenue;
	}

	public BigDecimal getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(BigDecimal totalCost) {
		this.totalCost = totalCost;
	}

	public BigDecimal getTotalProfit() {
		return totalProfit;
	}

	public void setTotalProfit(BigDecimal totalProfit) {
		this.totalProfit = totalProfit;
	}
	
	

	public BigDecimal getTotalDiscount() {
		return totalDiscount;
	}

	public void setTotalDiscount(BigDecimal totalDiscount) {
		this.totalDiscount = totalDiscount;
	}

	public ProfitSummaryDto() {

	}

	public ProfitSummaryDto(BigDecimal totalRevenue, BigDecimal totalCost, BigDecimal totalProfit) {
		super();
		this.totalRevenue = totalRevenue;
		this.totalCost = totalCost;
		this.totalProfit = totalProfit;
	}

}
