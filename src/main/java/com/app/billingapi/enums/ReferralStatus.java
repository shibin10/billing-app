package com.app.billingapi.enums;

public enum ReferralStatus {
	PENDING, // Referral created, waiting for sign-up
	COMPLETED, // Referred user signed up, reward given
	EXPIRED // Referral expired without use

}
