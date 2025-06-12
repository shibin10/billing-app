package com.app.billingapi.controller;

import com.app.billingapi.dto.InvoiceDto;
import com.app.billingapi.entity.Invoice;
import com.app.billingapi.service.InvoiceService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/invoice")
public class InvoiceController {

	@Autowired
	private InvoiceService invoiceService;

	@PostMapping("/add")
	public ResponseEntity<?> addInvoice(@RequestBody @Valid InvoiceDto invoiceDto) {
		try {
		Invoice invoice = invoiceService.saveInvoice(invoiceDto);
		return ResponseEntity.ok("Invoice created successfully with ID: " + invoice.getInvoiceId());
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Failed to create Invoice. Reason: " + ex.getMessage());
		}
		}

	@GetMapping("/all")
	public ResponseEntity<?> getAllInvoices() {
		List<InvoiceDto> invoices = invoiceService.findAllInvoices();
		if (invoices.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(invoices);
	}

	@GetMapping("/{invoiceId}")
	public ResponseEntity<?> getInvoiceById(@PathVariable Long invoiceId) {
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
		return ResponseEntity.ok("Invoice deleted successfully.");
	}
}
