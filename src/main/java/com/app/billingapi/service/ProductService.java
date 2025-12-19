package com.app.billingapi.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.app.billingapi.dto.ProductDto;
import com.app.billingapi.dto.ShopDto;
import com.app.billingapi.dto.SignupUserDto;
import com.app.billingapi.entity.Product;
import com.app.billingapi.entity.Shop;
import com.app.billingapi.entity.User;
import com.app.billingapi.exception.ProductNotFoundException;
import com.app.billingapi.exception.UserIllegalArgumentException;
import com.app.billingapi.repository.ProductRepository;
import com.app.billingapi.repository.ShopRepository;
import com.app.billingapi.util.JwtUtils;

@Service
public class ProductService {

	private final ProductRepository productRepository;
	private final ShopRepository shopRepository;
	private static final Logger logger = LoggerFactory.getLogger(ProductService.class);
	@Autowired
	private JwtUtils jwtUtils;

	public ProductService(ProductRepository productRepository, ShopRepository shopRepository) {
		this.productRepository = productRepository;
		this.shopRepository = shopRepository;
	}

	public Product saveProduct(ProductDto productDto, String token) {

		Long shopId = jwtUtils.extractShopId(token);
		Shop shop = shopRepository.findById(shopId)
				.orElseThrow(() -> new IllegalArgumentException("Invalid shop ID: " + shopId));

		boolean productNumberExists = productRepository
				.existsByProductNumberIgnoreCaseAndShopId(productDto.getProductNumber(), shop);

		if (productNumberExists) {
			throw new UserIllegalArgumentException(
					"Product with ProductNumber '" + productDto.getProductNumber() + "' already exists in this shop.",
					"Duplicate Product Number", 409);
		}

		Product product = new Product();
		product.setName(productDto.getName());
		product.setProductNumber(productDto.getProductNumber());
		product.setLocation(productDto.getLocation());
		product.setHsn(productDto.getHsn());
		product.setDescription(productDto.getDescription());
		product.setQuantity(productDto.getQuantity());
		product.setPurchasePrice(productDto.getPurchasePrice());
		product.setWholesaleRate(productDto.getWholesaleRate());
		product.setRetailRate(productDto.getRetailRate());
		product.setTaxRate(productDto.getTaxRate());
		product.setCgst(productDto.getCGST());
		product.setSgst(productDto.getSGST());
		product.setCategory(productDto.getCategory());
		product.setImageUrl(productDto.getImageUrl());
		product.setExpiry(productDto.getExpiry());
		product.setBarcode(productDto.getBarcode());
		product.setShopId(shop);
		return productRepository.save(product);

	}

	public boolean existsByName(String name) {
		return productRepository.existsByName(name);
	}

	public Page<ProductDto> findAllProducts(int page, int size, String search) {

		try {
			Pageable pageable = PageRequest.of(page, size, Sort.by("productId").descending());

			Page<Product> products;
			
			if (search == null || search.trim().isEmpty()) {
				products = productRepository.findAll(pageable);
			} else {

				String keyword = search.trim();
				 Long hsnNumber = null;

			        try {
			            hsnNumber = Long.parseLong(keyword); 
			        } catch (NumberFormatException e) {
			       
			        }
			        
				products = productRepository.findByNameContainingIgnoreCaseOrProductNumberContainingIgnoreCaseOrHsn(
						keyword, keyword, hsnNumber, pageable);
			}

			return products.map(this::mapToProductDto);

		} catch (Exception e) {
			logger.error("Error fetching invoices", e);
			throw new RuntimeException("Failed to fetch invoices", e);
		}
	}

	public ProductDto updateProduct(ProductDto productDto) {
		logger.info("Updating product: {}", productDto);
		try {

			Product product = productRepository.findById(productDto.getProductId())
					.orElseThrow(() -> new ProductNotFoundException(
							"Product not found with ID: " + productDto.getProductId(),
							"Please provide valid Product details.", 404));

			Shop shop = shopRepository.findById(productDto.getShopId())
					.orElseThrow(() -> new IllegalArgumentException("Invalid Shop Id: " + productDto.getShopId()));

			product.setName(productDto.getName());
			product.setProductNumber(productDto.getProductNumber());
			product.setLocation(productDto.getLocation());
			product.setHsn(productDto.getHsn());
			product.setDescription(productDto.getDescription());
			product.setQuantity(productDto.getQuantity());
			product.setPurchasePrice(productDto.getPurchasePrice());
			product.setWholesaleRate(productDto.getWholesaleRate());
			product.setRetailRate(productDto.getRetailRate());
			product.setTaxRate(productDto.getTaxRate());
			product.setCgst(productDto.getCGST());
			product.setSgst(productDto.getSGST());
			product.setCategory(productDto.getCategory());
			product.setImageUrl(productDto.getImageUrl());
			product.setExpiry(productDto.getExpiry());
			product.setBarcode(productDto.getBarcode());
			product.setShopId(shop);

			Product products = productRepository.save(product);

			return mapToProductDto(products);

		} catch (ProductNotFoundException e) {
			logger.warn("Product update failed: {}", e.getMessage(), e);
			throw e;
		} catch (IllegalArgumentException e) {
			logger.warn("Invalid input while updating product: {}", e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			logger.error("Unexpected error occurred while updating product ID: {}", productDto.getProductId(), e);
			throw new RuntimeException("Error updating product", e);
		}

	}

	public ProductDto findProductById(Long productId) {
		Optional<Product> productOpt = productRepository.findById(productId);
		if (productOpt.isPresent()) {
			System.out.println("Fetched Product: " + productOpt.get()); // should not be null
			return mapToProductDto(productOpt.get());
		} else {
			System.out.println("Product not found with ID: " + productId);
			return null;
		}
	}

	public ProductDto deleteProductById(Long productId) {
		logger.info("Deleting product by ID: {}", productId);

		try {
			Optional<Product> optionalProduct = productRepository.findById(productId);
			if (optionalProduct.isPresent()) {
				Product product = optionalProduct.get();

				productRepository.delete(product);
				return mapToProductDto(product);

			} else {

				return null;
			}
		} catch (DataIntegrityViolationException ex) {
			logger.error("Cannot delete product with ID: {} due to related records", productId, ex);

			throw new UserIllegalArgumentException("Cannot delete product because it is already used in an Invoice",
					"Product Deletion Not Allowed", 400);

		} catch (Exception e) {
			logger.error("Error deleting product by ID: {}", productId, e);
			throw new RuntimeException("Error deleting product", e);
		}
	}

	public ProductDto mapToProductDto(Product product) {
		ProductDto productDto = new ProductDto();

		productDto.setProductId(product.getProductId());
		productDto.setName(product.getName());
		productDto.setProductNumber(product.getProductNumber());
		productDto.setLocation(product.getLocation());
		productDto.setHsn(product.getHsn());
		productDto.setDescription(product.getDescription());
		productDto.setQuantity(product.getQuantity());
		productDto.setPurchasePrice(product.getPurchasePrice());
		productDto.setWholesaleRate(product.getWholesaleRate());
		productDto.setRetailRate(product.getRetailRate());
		productDto.setTaxRate(product.getTaxRate());
		productDto.setCGST(product.getCgst());
		productDto.setSGST(product.getSgst());
		productDto.setCategory(product.getCategory());
		productDto.setImageUrl(product.getImageUrl());
		productDto.setExpiry(product.getExpiry());
		productDto.setBarcode(product.getBarcode());

		Shop shop = product.getShopId();
		if (shop != null) {
			ShopDto shopDto = new ShopDto();
			shopDto.setShopId(shop.getShopId());
			shopDto.setName(shop.getName());
			shopDto.setPlace(shop.getPlace());
			shopDto.setStatus(shop.getStatus());
			shopDto.setMap(shop.getMap());
			productDto.setShop(shopDto);

			User user = shop.getOwner();
			if (user != null) {
				SignupUserDto userDto = new SignupUserDto();
				userDto.setUserId(user.getUserId());
				userDto.setFullName(user.getFullName());
				userDto.setEmail(user.getEmail());
				userDto.setPlace(user.getPlace());
				userDto.setPhone(user.getPhone());
				userDto.setStatus(user.getStatus());
				shopDto.setOwner(userDto);
			}
		}

		return productDto;

	}

	public boolean isProductNumberExists(String productNumber, Long shopId) {
		Shop shop = shopRepository.findById(shopId)
				.orElseThrow(() -> new UserIllegalArgumentException("Shop with ID '" + shopId + "' not found.",
						"Shop Not Found", 404));
		return productRepository.existsByProductNumberIgnoreCaseAndShopId(productNumber, shop);
	}

}
