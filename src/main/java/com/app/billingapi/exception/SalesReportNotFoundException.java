package com.app.billingapi.exception;

public class SalesReportNotFoundException extends RuntimeException {
	private final String title;
	private final int status;
	private static final long serialVersionUID = 1L;

	public SalesReportNotFoundException(String message , String title, int status) {
		super(message);
		this.title = title;
		this.status = status;
	}

	public String getTitle() {
		return title;
	}

	public int getStatus() {
		return status;
	}
}
