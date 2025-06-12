package com.app.billingapi.controller;

import com.app.billingapi.dto.SaleDto;
import com.app.billingapi.entity.Sale;
import com.app.billingapi.service.SaleService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sales")
public class SaleController {

	@Autowired
	private SaleService saleService;

	@PostMapping("/add")
	public ResponseEntity<?> addSale(@RequestBody @Valid SaleDto saleDto) {
		try {
			
			Sale sale = saleService.createSale(saleDto);
			
			return ResponseEntity.ok("Sale created successfully with ID: " + sale.getSaleId());
		
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Failed to create sale. Reason: " + ex.getMessage());
		}
	}

	@GetMapping("/all")
	public ResponseEntity<?> getAllSales() {
		List<SaleDto> sales = saleService.getAllSales();
		if (sales.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No sales found");
		} else {
			return ResponseEntity.ok().body(sales);
		}
	}

	@GetMapping("/{saleId}")
	public ResponseEntity<?> getSaleById(@PathVariable("saleId") Long saleId) {
		if (saleId == null) {
			return ResponseEntity.badRequest().body("Sale ID cannot be null");
		}
		SaleDto saleDto = saleService.getSaleById(saleId);
		if (saleDto == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Sale found with ID: " + saleId);
		} else {
			return ResponseEntity.ok().body(saleDto);
		}
	}

	@PostMapping("/update/{saleId}")
	public ResponseEntity<?> updateSale(@PathVariable("saleId") Long saleId, @RequestBody @Valid SaleDto saleDto) {
		if (saleId == null || saleDto == null) {
			return ResponseEntity.badRequest().body("Request cannot be empty");
		}
		if (!saleId.equals(saleDto.getSaleId())) {
			return ResponseEntity.badRequest().body("Invalid Sale ID in the request");
		}

		try {
			SaleDto updatedSale = saleService.updateSale(saleDto);
			return ResponseEntity.ok(updatedSale);
		} catch (IllegalArgumentException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		}
	}

	@DeleteMapping("/delete/{saleId}")
	public ResponseEntity<?> deleteSaleById(@PathVariable("saleId") Long saleId) {
		if (saleId == null) {
			return ResponseEntity.badRequest().body("Sale ID cannot be null");
		}

		try {
			saleService.deleteSale(saleId);
			return ResponseEntity.ok("Sale deleted successfully");
		} catch (IllegalArgumentException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		}
	}
}
