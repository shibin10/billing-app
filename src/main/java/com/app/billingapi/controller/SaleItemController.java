package com.app.billingapi.controller;

import com.app.billingapi.dto.SaleItemDto;
import com.app.billingapi.entity.SaleItem;
import com.app.billingapi.service.SaleItemService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/sales-items")
public class SaleItemController {

	@Autowired
	private SaleItemService saleItemService;

	@PostMapping("/add")
	public ResponseEntity<?> addSaleItems(@RequestBody @Valid List<SaleItemDto> saleItemDtos) {
	   
		try {
	        List<SaleItemDto> createdSaleItems = new ArrayList<>();
	       
	        for (SaleItemDto dto : saleItemDtos ) {
	        	SaleItem saved = saleItemService.createSaleItem(dto);
	        	SaleItemDto responseDto = saleItemService.mapToDto(saved);
	        	createdSaleItems.add(responseDto);
	        }
	        
	        return ResponseEntity.ok("Sale items created successfully with IDs: " );
	    
		} catch (IllegalArgumentException e) {
	        return ResponseEntity.badRequest().body(e.getMessage());
	   
	    } catch (Exception ex) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body("Failed to create sale items. Reason: " + ex.getMessage());
	    }
	}


	@GetMapping("/all")
	public ResponseEntity<?> getAllSaleItems() {
		List<SaleItemDto> items = saleItemService.getAllSaleItems();
	
		if (items.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No sale items found");
		} else {
			return ResponseEntity.ok(items);
		}
	}

	@GetMapping("/{itemId}")
	public ResponseEntity<?> getSaleItemById(@PathVariable("itemId") Long itemId) {
		
		if (itemId == null) {
			return ResponseEntity.badRequest().body("Sale item ID cannot be null");
		}
		try {
		
			SaleItemDto item = saleItemService.getSaleItemById(itemId);
			return ResponseEntity.ok(item);
		
		} catch (IllegalArgumentException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		
		 }
	}

	@PostMapping("/update/{itemId}")
	public ResponseEntity<?> updateSaleItem(@PathVariable("itemId") Long itemId, @RequestBody @Valid SaleItemDto saleItemDto) {
		if (itemId == null || saleItemDto == null) {
			return ResponseEntity.badRequest().body("Request cannot be empty");
		}
		if (!itemId.equals(saleItemDto.getSaleItemId())) {
			return ResponseEntity.badRequest().body("Invalid SaleItem ID in the request");
		}
		try {
			SaleItemDto updatedItem = saleItemService.updateSaleItem(saleItemDto);
			return ResponseEntity.ok(updatedItem);
		} catch (IllegalArgumentException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		}
	}

	@DeleteMapping("/delete/{itemId}")
	public ResponseEntity<?> deleteSaleItem(@PathVariable("itemId") Long itemId) {
		if (itemId == null) {
			return ResponseEntity.badRequest().body("Sale item ID cannot be null");
		}
		try {
			saleItemService.deleteSaleItem(itemId);
			return ResponseEntity.ok("Sale item deleted successfully");
		} catch (IllegalArgumentException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		}
	}
}
