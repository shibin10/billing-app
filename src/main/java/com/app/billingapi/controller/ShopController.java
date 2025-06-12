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

import com.app.billingapi.dto.ShopDto;
import com.app.billingapi.dto.SubscriptionPlanDto;
import com.app.billingapi.entity.Shop;
import com.app.billingapi.service.ShopService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/shop")
public class ShopController {

	@Autowired
	ShopService shopService;


	@PostMapping("/add")
	public ResponseEntity<?> addShop(@RequestBody ShopDto shopDto) {

		ShopDto createdShop = shopService.saveShop(shopDto);
		return ResponseEntity.ok(" Shop Created successfully with ID: " + createdShop.getShopId());
	}
	
	@GetMapping("/all")
	public ResponseEntity<?> getAllShop() {
		List<ShopDto> shops = shopService.findAllShops();
		if (shops.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Shops found");
		} else {
			return ResponseEntity.ok().body(shops);
		}

	}

	@GetMapping("{shopId}")
	public ResponseEntity<?> getShopById(@PathVariable("shopId") @Valid Long shopId) {
		if (shopId == null) {
			return ResponseEntity.badRequest().body("Shop Id cannot be null");
		}
		ShopDto shopDto = shopService.findShopById(shopId);
		if (shopDto == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Shops found");
		} else {
			return ResponseEntity.ok().body(shopDto);
		}
	}

	@PostMapping("/update/{shopId}")
	public ResponseEntity<?> updateShop(@PathVariable("shopId") @Valid Long shopId,
			@Valid @RequestBody ShopDto shopDto) {

		if (shopDto == null || shopId == null) {
			return ResponseEntity.badRequest().body("Request cannot be empty ");
		}

		if (!shopId.equals(shopDto.getShopId())) {
			return ResponseEntity.badRequest().body("Invalid Shop ID in the request");

		}

		Shop shop = shopService.updateShop(shopDto);
		if (shop == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Shop found");
		} else {
			return ResponseEntity.ok().body(shop);
		}

	}

	@DeleteMapping("/delete/{shopId}")
	public ResponseEntity<?> deleteUserById(@PathVariable Long shopId) {
		if (shopId==null) {
			return ResponseEntity.badRequest().body("Shop ID cannot be empty");
		}
		Shop shop = shopService.deleteShopById(shopId);
		if (shop == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Shop not found with ID: " + shopId);

		}
		return ResponseEntity.ok().body("Shop deleted successfully");
	}

}
