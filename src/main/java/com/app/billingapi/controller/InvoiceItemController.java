package com.app.billingapi.controller;

import com.app.billingapi.dto.InvoiceItemDto;
import com.app.billingapi.entity.InvoiceItem;
import com.app.billingapi.service.InvoiceItemService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/invoice-items")
public class InvoiceItemController {

    @Autowired
    private InvoiceItemService invoiceItemService;

    @PostMapping("/add")
    public ResponseEntity<?> addInvoiceItem(@RequestBody @Valid InvoiceItemDto invoiceItemDto) {
        InvoiceItem item = invoiceItemService.addInvoiceItem(invoiceItemDto);
        return ResponseEntity.ok("Invoice item added successfully with ID: " + item.getInvoiceItemId());
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllInvoiceItems() {
        List<InvoiceItemDto> items = invoiceItemService.getAllInvoiceItems();
        if (items.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(items);
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<?> getInvoiceItemById(@PathVariable Long itemId) {
        InvoiceItemDto item = invoiceItemService.getInvoiceItemById(itemId);
        if (item == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(item);
    }

    @PostMapping("/update/{invoiceItemId}")
    public ResponseEntity<?> updateInvoiceItem(@PathVariable Long invoiceItemId, @RequestBody @Valid InvoiceItemDto invoiceItemDto) {
        if (!invoiceItemId.equals(invoiceItemDto.getInvoiceItemId())) {
            return ResponseEntity.badRequest().body("Mismatched invoice item ID");
        }

        InvoiceItem updatedItem = invoiceItemService.updateInvoiceItem(invoiceItemId, invoiceItemDto);
        return ResponseEntity.ok(updatedItem);
    }


    @DeleteMapping("/delete/{itemId}")
    public ResponseEntity<?> deleteInvoiceItem(@PathVariable Long itemId) {
        invoiceItemService.deleteInvoiceItem(itemId);
        return ResponseEntity.ok("Invoice item deleted successfully.");
    }
}
