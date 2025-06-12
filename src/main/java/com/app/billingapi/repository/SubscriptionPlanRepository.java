package com.app.billingapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.billingapi.entity.SubscriptionPlan;

public interface SubscriptionPlanRepository  extends JpaRepository<SubscriptionPlan,Long>{

}
