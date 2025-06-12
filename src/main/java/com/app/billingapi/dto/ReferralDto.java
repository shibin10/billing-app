package com.app.billingapi.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.app.billingapi.enums.ReferralStatus;
import com.app.billingapi.enums.RewardType;

public class ReferralDto {

    private Long referralId;
    private Long referrerId;
    private Long referredUserId;
    private String referralCode;
    private RewardType rewardType;
    private BigDecimal rewardValue;
    private Boolean isRewardClaimed;
    private LocalDate referralDate;
    private ReferralStatus status;

    // Constructors
    public ReferralDto() {}

    public ReferralDto(Long referralId, Long referrerId, Long referredUserId, String referralCode, 
                       RewardType rewardType, BigDecimal rewardValue, Boolean isRewardClaimed, 
                       LocalDate referralDate, ReferralStatus status) {
        this.referralId = referralId;
        this.referrerId = referrerId;
        this.referredUserId = referredUserId;
        this.referralCode = referralCode;
        this.rewardType = rewardType;
        this.rewardValue = rewardValue;
        this.isRewardClaimed = isRewardClaimed;
        this.referralDate = referralDate;
        this.status = status;
    }

    public Long getReferralId() {
        return referralId;
    }

    public void setReferralId(Long referralId) {
        this.referralId = referralId;
    }

    public Long getReferrerId() {
        return referrerId;
    }

    public void setReferrerId(Long referrerId) {
        this.referrerId = referrerId;
    }

    public Long getReferredUserId() {
        return referredUserId;
    }

    public void setReferredUserId(Long referredUserId) {
        this.referredUserId = referredUserId;
    }

    public String getReferralCode() {
        return referralCode;
    }

    public void setReferralCode(String referralCode) {
        this.referralCode = referralCode;
    }

    public RewardType getRewardType() {
        return rewardType;
    }

    public void setRewardType(RewardType rewardType) {
        this.rewardType = rewardType;
    }

    public BigDecimal getRewardValue() {
        return rewardValue;
    }

    public void setRewardValue(BigDecimal rewardValue) {
        this.rewardValue = rewardValue;
    }

    public Boolean getIsRewardClaimed() {
        return isRewardClaimed;
    }

    public void setIsRewardClaimed(Boolean isRewardClaimed) {
        this.isRewardClaimed = isRewardClaimed;
    }

    public LocalDate getReferralDate() {
        return referralDate;
    }

    public void setReferralDate(LocalDate referralDate) {
        this.referralDate = referralDate;
    }

    public ReferralStatus getStatus() {
        return status;
    }

    public void setStatus(ReferralStatus status) {
        this.status = status;
    }
}

