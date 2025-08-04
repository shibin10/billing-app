package com.app.billingapi.dto;

import java.math.BigDecimal;

public class TaxSummaryDto {
    private BigDecimal cgst;
    private BigDecimal sgst;
    private BigDecimal igst;

    public TaxSummaryDto(BigDecimal cgst, BigDecimal sgst, BigDecimal igst) {
        this.cgst = cgst;
        this.sgst = sgst;
        this.igst = igst;
    }

	public BigDecimal getCgst() {
		return cgst;
	}

	public void setCgst(BigDecimal cgst) {
		this.cgst = cgst;
	}

	public BigDecimal getSgst() {
		return sgst;
	}

	public void setSgst(BigDecimal sgst) {
		this.sgst = sgst;
	}

	public BigDecimal getIgst() {
		return igst;
	}

	public void setIgst(BigDecimal igst) {
		this.igst = igst;
	}

 
}

