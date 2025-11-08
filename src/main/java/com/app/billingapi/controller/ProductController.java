package com.app.billingapi.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.billingapi.dto.ProductDto;
import com.app.billingapi.entity.Product;
import com.app.billingapi.service.ProductService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/products")
public class ProductController {

	@Autowired
	ProductService productService;
	
	 

	  @PostMapping("/add")
	  public ResponseEntity<?> addMultipleProducts(@RequestHeader("Authorization") String authHeader,
	                                               @RequestBody List<@Valid ProductDto> productDtos) {

	      String token = authHeader.replace("Bearer ", "");
	      List<ProductDto> savedProducts = new ArrayList<>();

	      for (ProductDto dto : productDtos) {
	          Product saved = productService.saveProduct(dto, token);
	          ProductDto responseDto = productService.mapToProductDto(saved);
	          savedProducts.add(responseDto);
	      }

	      return ResponseEntity.ok(savedProducts);
	  }

	 
	@GetMapping("/all")
	public ResponseEntity<?> getAllProduct(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size)  {
		
		Page<ProductDto> product = productService.findAllProducts(page, size);
		
		if (product.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Products Found");
		} else {
			return ResponseEntity.ok().body(product);
		}
	}

	@PostMapping("/update/{productId}")
	public ResponseEntity<?> updateProduct(@PathVariable Long productId, @RequestBody @Valid ProductDto productDto) {

		if (!productId.equals(productDto.getProductId())) {
			return ResponseEntity.badRequest().body("Product ID mismatch.");
		}
		ProductDto updatedProductDto = productService.updateProduct(productDto);
		return ResponseEntity.ok(updatedProductDto);
	}

	@DeleteMapping("/delete/{productId}")
	public ResponseEntity<?> deleteProductById(@PathVariable("productId") @Valid Long productId) {

		if (productId == null) {
			return ResponseEntity.badRequest().body("Product Id cannot be Null");
		}
		ProductDto product = productService.deleteProductById(productId);
		if (product == null) {
			ResponseEntity.status(HttpStatus.NOT_FOUND).body("product not found with ID: " + productId);
		}
		return ResponseEntity.ok().body("product deleted successfully");

	}

	@GetMapping("{productId}")
	public ResponseEntity<?> getProductById(@PathVariable("productId") @Valid Long productId) {
		if (productId == null) {
			return ResponseEntity.badRequest().body("Product Id cannot be null");
		}
		ProductDto productDto = productService.findProductById(productId);
		if (productDto == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Product found");
		} else {
			return ResponseEntity.ok().body(productDto);
		}
	}
	
	 @GetMapping("/product-number-exist")
	    public ResponseEntity<Map<String, Object>> checkProductNumber(
	            @RequestParam String productNumber,
	            @RequestParam Long shopId) {

	        boolean exists = productService.isProductNumberExists(productNumber, shopId);

	        Map<String, Object> response = new HashMap<>();
	        response.put("productNumber", productNumber);
	        response.put("exists", exists);

	        return ResponseEntity.ok(response);
	    }

}
