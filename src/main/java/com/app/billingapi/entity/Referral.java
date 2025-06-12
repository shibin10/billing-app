package com.app.billingapi.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import com.app.billingapi.enums.ReferralStatus;
import com.app.billingapi.enums.RewardType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "referral")
public class Referral implements Serializable {
	private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "referralid")
    private Long referralId;

    @ManyToOne
    @JoinColumn(name = "referrer_id", referencedColumnName = "userid", nullable = false)
    private User referrer; // User who shared the code

    @ManyToOne
    @JoinColumn(name = "referred_user_id", referencedColumnName = "userid", nullable = true)
    private User referredUser; // New user who signed up

    @Column(name = "referral_code", unique = true, nullable = false)
    private String referralCode; // Unique code used for tracking

    @Column(name = "reward_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private RewardType rewardType; // Discount, Cashback, etc.

    @Column(name = "reward_value", nullable = false)
    private BigDecimal rewardValue; // e.g., ₹100 discount

    @Column(name = "is_reward_claimed", nullable = false)
    private Boolean isRewardClaimed = false; // Whether reward is used

    @Column(name = "referral_date", nullable = false)
    private LocalDate referralDate;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private ReferralStatus status; // PENDING, COMPLETED, EXPIRED
}
