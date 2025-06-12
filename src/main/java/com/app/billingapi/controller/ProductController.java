package com.app.billingapi.controller;

import java.util.ArrayList;
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

import com.app.billingapi.dto.ProductDto;
import com.app.billingapi.entity.Product;
import com.app.billingapi.service.ProductService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/products")
public class ProductController {

	@Autowired
	ProductService productService;

	@PostMapping("/adds")
	public ResponseEntity<?> addProduct(@RequestBody ProductDto productDto) {
		if (productService.existsByName(productDto.getName())) {
			return ResponseEntity.badRequest().body("Product already Exist");
		}
		Product productid = productService.saveProduct(productDto);
		String message = String.format("Product Added successfully!", productid);
		return ResponseEntity.ok(message);

	}
	
	@PostMapping("/add")
	public ResponseEntity<?> addMultipleProducts(@RequestBody List<@Valid ProductDto> productDtos) {
	    List<ProductDto> savedProducts = new ArrayList<>();
	    for (ProductDto dto : productDtos) {
	        Product saved = productService.saveProduct(dto);
	        ProductDto responseDto = productService.mapToProductDto(saved);
	        savedProducts.add(responseDto);
	    }
	    return ResponseEntity.ok(savedProducts);
	}


	@GetMapping("/all")
	public ResponseEntity<?> getAllProduct() {
		List<ProductDto> product = productService.findAllProducts();
		if (product.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Products Found");
		} else {
			return ResponseEntity.ok().body(product);
		}
	}

	@PostMapping("/update/{productId}")
	public ResponseEntity<?> updateProduct(@PathVariable("productId") Long productId,
			@Valid @RequestBody ProductDto productDto) {
		System.out.println("Received request to update product with ID: " + productId);
		System.out.println("ProductDto received: " + productDto);

		if (productId == null || productDto == null) {
			System.out.println("Bad request: Product ID or ProductDto is null");
			return ResponseEntity.badRequest().body("Product Id cannot be null");
		}

		if (!productId.equals(productDto.getProductId())) {
			System.out.println("Bad request: Product ID in path and body do not match");
			return ResponseEntity.badRequest().body("Invalid Product ID in the request");
		}

		System.out.println("Calling productService.updateProduct()");
		ProductDto updatedProductDto = productService.updateProduct(productDto);

		if (updatedProductDto == null) {
			System.out.println("Update failed: Product not found");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product Not found");
		} else {
			System.out.println("Product updated successfully: " + updatedProductDto);
			return ResponseEntity.ok().body(updatedProductDto);
		}
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

}
