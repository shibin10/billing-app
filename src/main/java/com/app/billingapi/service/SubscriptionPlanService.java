package com.app.billingapi.service;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.app.billingapi.dto.SubscriptionPlanDto;
import com.app.billingapi.entity.SubscriptionPlan;
import com.app.billingapi.repository.SubscriptionPlanRepository;

@Service
public class SubscriptionPlanService {
	 private final SubscriptionPlanRepository subscriptionPlanRepository;	  
	   
	    public SubscriptionPlanService(SubscriptionPlanRepository subscriptionPlanRepository) {
	        this.subscriptionPlanRepository = subscriptionPlanRepository;
	    }
	 
	 public SubscriptionPlanDto addPlan(SubscriptionPlanDto planDto) {
	        SubscriptionPlan plan = new SubscriptionPlan();
	      
	        plan.setPlanName(planDto.getPlanName());
	        plan.setPrice(planDto.getPrice());
	        plan.setDuration(planDto.getDuration());
	        plan.setFeatures(planDto.getFeatures());

	        SubscriptionPlan savedPlan = subscriptionPlanRepository.save(plan);
	        return mapToDto(savedPlan);
	    }
	 
	 private SubscriptionPlanDto mapToDto(SubscriptionPlan plan) {
	        SubscriptionPlanDto dto = new SubscriptionPlanDto();
			
	        dto.setPlanId(plan.getPlanId());
	        dto.setPlanName(plan.getPlanName());
	        dto.setPrice(plan.getPrice());
	        dto.setDuration(plan.getDuration());
	        dto.setFeatures(plan.getFeatures());
	        return dto;
	    }
	 
	 public List<SubscriptionPlanDto> getAllPlans() {
	        List<SubscriptionPlan> plans = subscriptionPlanRepository.findAll();
	        return plans.stream().map(this::mapToDto).collect(Collectors.toList());
	    }
	 
	 public SubscriptionPlanDto getPlanById(Long id) {
	        SubscriptionPlan plan = subscriptionPlanRepository.findById(id)
	                .orElseThrow(() -> new RuntimeException("Plan not found with ID: " + id));
	        return mapToDto(plan);
	    }
	 
	 public SubscriptionPlanDto updatePlan( SubscriptionPlanDto planDto) {
	        SubscriptionPlan existingPlan = subscriptionPlanRepository.findById(planDto.getPlanId())
	                .orElseThrow(() -> new RuntimeException("Plan not found with ID: " + planDto.getPlanId()));

	        existingPlan.setPlanName(planDto.getPlanName());
	        existingPlan.setPrice(planDto.getPrice());
	        existingPlan.setDuration(planDto.getDuration());
	        existingPlan.setFeatures(planDto.getFeatures());

	        SubscriptionPlan updatedPlan = subscriptionPlanRepository.save(existingPlan);
	        return mapToDto(updatedPlan);
	    }
	 public SubscriptionPlanDto deletePlan(Long planId) {
	        if (!subscriptionPlanRepository.existsById(planId)) {
	            throw new RuntimeException("Plan not found with ID: " + planId);
	        }
	        subscriptionPlanRepository.deleteById(planId);
			return null;
	    }
}
