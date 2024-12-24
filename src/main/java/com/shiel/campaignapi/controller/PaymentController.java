/*package com.shiel.campaignapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shiel.campaignapi.dto.PaymentGatewayResponse;
import com.shiel.campaignapi.dto.PaymentNotificationDto;
import com.shiel.campaignapi.dto.PaymentRequestDto;
import com.shiel.campaignapi.service.PaymentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

	@Autowired
	private final PaymentService paymentService;

	public PaymentController(PaymentService paymentService) {
		this.paymentService = paymentService;
	}
	
	@PostMapping("/initiate")
	public ResponseEntity<PaymentGatewayResponse> initiatePayment(@RequestBody PaymentRequestDto paymentRequestDto) {
		try {
			PaymentGatewayResponse response = paymentService.initiatePayment(paymentRequestDto);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(null);
		}
	}

	@PostMapping("/return")
	public ResponseEntity<?> handleReturnUrl(@RequestBody @Valid PaymentNotificationDto paymentNotificationDto) {
		try {
			paymentService.handlePaymentNotification(paymentNotificationDto);
			return ResponseEntity.ok("Payment processed successfully.");
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("Error processing payment: " + e.getMessage());
		}
	}

	@PostMapping("/notify")
	public ResponseEntity<String> handlePaymentNotification(@RequestBody PaymentNotificationDto paymentNotification) {
		try {
			paymentService.handlePaymentNotification(paymentNotification);
			return ResponseEntity.ok("Payment notification processed successfully.");
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("Error processing payment notification: " + e.getMessage());
		}
	}
}*/
