package com.app.billingapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.billingapi.entity.SubscriptionPlan;
import com.app.billingapi.enums.PlanType;

public interface SubscriptionPlanRepository  extends JpaRepository<SubscriptionPlan,Long>{
	boolean existsByPlanName(PlanType planName);



}
