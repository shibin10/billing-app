package com.app.billingapi.dto;

import java.math.BigDecimal;
import java.util.List;

public class SalesReportResponse {

	private List<SaleReportDto> sales;
	private BigDecimal totalFinalAmount;
	private BigDecimal totalTax;
	private int invoiceCount;

	public List<SaleReportDto> getSales() {
		return sales;
	}

	public void setSales(List<SaleReportDto> sales) {
		this.sales = sales;
	}

	public BigDecimal getTotalFinalAmount() {
		return totalFinalAmount;
	}

	public void setTotalFinalAmount(BigDecimal totalFinalAmount) {
		this.totalFinalAmount = totalFinalAmount;
	}

	public BigDecimal getTotalTax() {
		return totalTax;
	}

	public void setTotalTax(BigDecimal totalTax) {
		this.totalTax = totalTax;
	}

	public int getInvoiceCount() {
		return invoiceCount;
	}

	public void setInvoiceCount(int invoiceCount) {
		this.invoiceCount = invoiceCount;
	}

}
