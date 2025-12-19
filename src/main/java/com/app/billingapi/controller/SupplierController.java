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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.billingapi.dto.SupplierDto;
import com.app.billingapi.service.SupplierService;
import com.app.billingapi.util.JwtUtils;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/suppliers")
public class SupplierController {

	@Autowired
	private SupplierService supplierService;

	@Autowired
	private JwtUtils jwtUtils;

	@PostMapping("/add")
	public ResponseEntity<?> addSupplier(@RequestBody SupplierDto supplierDto,
										@RequestHeader("Authorization") String token) {


		if (supplierService.existsByPhone(supplierDto.getPhone())) {
			return ResponseEntity.badRequest().body("Phone Number already exists");
		}
		
		SupplierDto savedSupplier = supplierService.addSupplier(supplierDto,token);
		return ResponseEntity.status(HttpStatus.CREATED).body(savedSupplier);
	}

	@GetMapping("/all")
	public ResponseEntity<?> getAllSuppliers(@RequestHeader("Authorization") String token) {

	    Long shopId = jwtUtils.extractShopId(token);
	    List<SupplierDto> suppliers = supplierService.getAllSuppliers(shopId);
	    return ResponseEntity.ok(suppliers);
	}


	@GetMapping("/{supplierId}")
	public ResponseEntity<?> getSupplierById(@PathVariable("supplierId")@Valid  Long supplierId) {
		
		SupplierDto supplierDto = supplierService.getSupplierById(supplierId);
		return ResponseEntity.ok(supplierDto);
	}

	@PostMapping("/update/{supplierId}")
	public ResponseEntity<?> updateSupplier(@PathVariable Long supplierId, @RequestBody SupplierDto supplierDto) {

		SupplierDto updated = supplierService.updateSupplier(supplierId, supplierDto);
		return ResponseEntity.ok(updated);
	}

	@DeleteMapping("/delete/{supplierId}")
	public ResponseEntity<Void> deleteSupplier(@PathVariable Long supplierId) {
		supplierService.deleteSupplier(supplierId);
		return ResponseEntity.noContent().build();
	}
}
