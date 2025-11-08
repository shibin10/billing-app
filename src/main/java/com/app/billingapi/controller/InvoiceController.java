package com.app.billingapi.controller;

import com.app.billingapi.dto.InvoiceDto;
import com.app.billingapi.service.InvoiceService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/invoice")
public class InvoiceController {

	@Autowired
	private InvoiceService invoiceService;

	@PostMapping("/add")
	public ResponseEntity<?> addInvoice(@RequestBody @Valid InvoiceDto invoiceDto) {
		
		try {
			InvoiceDto invoice = invoiceService.saveInvoice(invoiceDto);
			
			return ResponseEntity
			            .status(HttpStatus.CREATED)
			            .body(invoice);
		
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Failed to create Invoice. Reason: " + ex.getMessage());
		}
	}

	@GetMapping("/all")
	public ResponseEntity<?> getAllInvoices( 
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		
		 Page<InvoiceDto> invoices = invoiceService.findAllInvoices(page, size);
		
		 if (invoices.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(invoices);
	}

	@GetMapping("/{invoiceId}")
	public ResponseEntity<?> getInvoiceById(@PathVariable("invoiceId") Long invoiceId) {
		InvoiceDto invoice = invoiceService.findInvoiceById(invoiceId);
		if (invoice == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(invoice);
	}

	@PostMapping("/update/{invoiceId}")
	public ResponseEntity<?> updateInvoice(@PathVariable Long invoiceId, @RequestBody @Valid InvoiceDto invoiceDto) {

		if (!invoiceId.equals(invoiceDto.getInvoiceId())) {
			return ResponseEntity.badRequest().body("Invoice ID mismatch.");
		}
		InvoiceDto updatedInvoiceDto = invoiceService.updateInvoice(invoiceDto);
		return ResponseEntity.ok(updatedInvoiceDto);
	}

	@DeleteMapping("/delete/{invoiceId}")
	public ResponseEntity<?> deleteInvoice(@PathVariable Long invoiceId) {
	    invoiceService.deleteInvoice(invoiceId);
	    return ResponseEntity.ok("Invoice deleted successfully")	;
	}
	
	@GetMapping("/partno/{productNumber}")
	public ResponseEntity<List<InvoiceDto>> getInvoicesByProductNumber(@PathVariable String productNumber) {
	    return ResponseEntity.ok(invoiceService.getInvoicesByProductNumber(productNumber));
	}

	@GetMapping("/count")
	public ResponseEntity<?> getInvoiceCount() {
	    long count = invoiceService.getTotalInvoiceCount();
	    return ResponseEntity.ok(Collections.singletonMap("totalInvoices", count));
	}
	
	@GetMapping("/total-amount")
	public ResponseEntity<?> getTotalInvoiceAmount() {
		BigDecimal  totalAmount = invoiceService.getTotalInvoiceAmount();
	    return ResponseEntity.ok(Collections.singletonMap("totalAmount", totalAmount));
	}


}
