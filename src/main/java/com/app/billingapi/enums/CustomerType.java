package com.app.billingapi.enums;

public enum CustomerType {
    CUSTOMER,     // Regular retail customer
    DEALER,       // Wholesale customer or reseller
    CREDIT,       // Buys on credit, payment due later
    SUBSCRIPTION  // Regular, recurring purchases (monthly, weekly, etc.)
}