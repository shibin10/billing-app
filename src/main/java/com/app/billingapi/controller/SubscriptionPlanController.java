package com.app.billingapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.billingapi.dto.SubscriptionPlanDto;
import com.app.billingapi.service.SubscriptionPlanService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/plan")
public class SubscriptionPlanController {

	@Autowired
	private SubscriptionPlanService subscriptionPlanService;

	@PostMapping("/add")
	public ResponseEntity<?> addPlan(@RequestBody SubscriptionPlanDto planDto) {

		SubscriptionPlanDto createdPlan = subscriptionPlanService.addPlan(planDto);
		return ResponseEntity.ok(" Plan Created successfully with ID: " + createdPlan.getPlanId());
	}

	@GetMapping("/all")
	public ResponseEntity<List<SubscriptionPlanDto>> getAllPlans() {
		List<SubscriptionPlanDto> plans = subscriptionPlanService.getAllPlans();
		return ResponseEntity.ok(plans);
	}

	
	
	@GetMapping("/{planId}")
	public ResponseEntity<?> getCustomerById(@PathVariable("planId")@Valid Long planId) {
		if (planId == null) {
			return ResponseEntity.badRequest().body("Plan ID cannot be null");
		}
		SubscriptionPlanDto subscriptionPlanDto = subscriptionPlanService.getPlanById(planId);
		if (subscriptionPlanDto == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(" Plan not found");
		}
		return ResponseEntity.ok(subscriptionPlanDto);
	}
	


	
	@PostMapping("/update/{planId}")
	public ResponseEntity<?> updatedPlan(@PathVariable("planId") Long planId,
			@Valid @RequestBody SubscriptionPlanDto subscriptionPlanDto) {
		
		if (planId == null || subscriptionPlanDto == null) {
			return ResponseEntity.badRequest().body("Plan ID or Plan details cannot be null");
		}
		if (!planId.equals(subscriptionPlanDto.getPlanId())) {
			return ResponseEntity.badRequest().body("Invalid Plan ID in the request");
		}
		
	SubscriptionPlanDto updatedPlan = subscriptionPlanService.updatePlan(subscriptionPlanDto);
		if (updatedPlan == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(" Plan not found");
		}
		return ResponseEntity.ok(updatedPlan);
	}

	@DeleteMapping("delete/{planId}")
	public ResponseEntity<?> deletePlan(@PathVariable Long planId) {
		subscriptionPlanService.deletePlan(planId);
		  return ResponseEntity.ok("Plan deleted successfully");
	}
}
