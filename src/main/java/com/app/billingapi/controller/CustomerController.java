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

import com.app.billingapi.dto.CustomerDto;
import com.app.billingapi.entity.Customer;
import com.app.billingapi.service.CustomerService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/customer")
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	@PostMapping("/add")
	public ResponseEntity<?> addCustomer(@RequestBody CustomerDto customerDto) {
		
		
	
		
		if (customerService.existsByPhone(customerDto.getPhone())) {
			return ResponseEntity.badRequest().body("Phone Number already exists");
		}
		Customer customer = customerService.saveCustomer(customerDto);
		return ResponseEntity.ok(" Customer registered successfully with ID: " + customer.getCustomerId());
	}

	@GetMapping("/all")
	public ResponseEntity<?> getAllCustomers() {
		List<CustomerDto> customers = customerService.findAllCustomers();
		if (customers.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No  Customers found");
		}
		return ResponseEntity.ok(customers);
	}

   

	@PostMapping("/update/{customerId}")
	public ResponseEntity<?> updateCustomer(@PathVariable("customerId") Long customerId,
			@Valid @RequestBody CustomerDto customerDto) {
		
		if (customerId == null || customerDto == null) {
			return ResponseEntity.badRequest().body("Customer ID or Customer details cannot be null");
		}
		if (!customerId.equals(customerDto.getCustomerId())) {
			return ResponseEntity.badRequest().body("Invalid Customer ID in the request");
		}
		
		CustomerDto updatedCustomerdto = customerService.updateCustomer(customerDto);
		if (updatedCustomerdto == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(" Customer not found");
		}
		return ResponseEntity.ok(updatedCustomerdto);
	}

	@DeleteMapping("/delete/{customerId}")
	public ResponseEntity<?> deleteCustomerById(@PathVariable("customerId") Long customerId) {
		if (customerId == null) {
			return ResponseEntity.badRequest().body("Customer ID cannot be null");
		}
		CustomerDto customerDto = customerService.deleteCustomerById(customerId);
		if (customerDto == null) {
			ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer not found with ID: " + customerId);
		}
		return ResponseEntity.ok(" customer deleted successfully");
	}

	@GetMapping("/{customerId}")
	public ResponseEntity<?> getCustomerById(@PathVariable("customerId")@Valid Long customerId) {
		if (customerId == null) {
			return ResponseEntity.badRequest().body("Customer ID cannot be null");
		}
		CustomerDto customerDto = customerService.findCustomerById(customerId);
		if (customerDto == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(" customer not found");
		}
		return ResponseEntity.ok(customerDto);
	}
	
	@GetMapping("/place/{place}")
    public ResponseEntity<?> getCustomersByPlace(@PathVariable("place") String place) {
        List<CustomerDto> customers = customerService.findCustomersByPlace(place);
        if (customers.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No customers found for the specified place");
        }
        return ResponseEntity.ok(customers);
    }
}
