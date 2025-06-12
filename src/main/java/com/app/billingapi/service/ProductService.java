package com.app.billingapi.service;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

@Service
public class ProductService {

	private final ProductRepository productRepository;
	private final ShopRepository shopRepository;
	private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

	public ProductService(ProductRepository productRepository, ShopRepository shopRepository) {
		this.productRepository = productRepository;
		this.shopRepository = shopRepository;
	}

	public Product saveProduct(ProductDto productDto) {

		Shop shop = shopRepository.findById(productDto.getShopId())
				.orElseThrow(() -> new IllegalArgumentException("Invalid Shop Id: " + productDto.getShopId()));

		logger.info("Save product: {}", productDto);
		
		
			
			if (productDto.getName() == null || productDto.getName().trim().isEmpty()) {
				throw new UserIllegalArgumentException("Missing product name", "Product name is required", 400);
			}
			if (productDto.getDescription() != null && productDto.getDescription().length() > 1000) {
				throw new UserIllegalArgumentException("Description too long",
						"Product description exceeds the maximum allowed length", 400);
			}
			if (productDto.getQuantity() <= 0) {
				throw new UserIllegalArgumentException("Invalid quantity", "Quantity cannot be negative", 400);
			}
			if (productDto.getImageUrl() != null && !productDto.getImageUrl().trim().isEmpty()) {
				try {
					new URL(productDto.getImageUrl());
				} catch (MalformedURLException e) {
					throw new UserIllegalArgumentException("Invalid image URL", "Image URL is malformed", 400);
				}
			}

			if (productDto.getOurPrice() == null || productDto.getOurPrice().compareTo(BigDecimal.ZERO) < 0) {
				throw new UserIllegalArgumentException("Invalid our price",
						"Our price must be provided and cannot be negative", 400);
			}
			if (productDto.getWholesaleRate() == null || productDto.getWholesaleRate().compareTo(BigDecimal.ZERO) < 0) {
				throw new UserIllegalArgumentException("Invalid wholesale rate",
						"Wholesale rate must be provided and cannot be negative", 400);
			}
			if (productDto.getRetailRate() == null || productDto.getRetailRate().compareTo(BigDecimal.ZERO) < 0) {
				throw new UserIllegalArgumentException("Invalid retail rate",
						"Retail rate must be provided and cannot be negative", 400);
			}
			if (productDto.getTaxRate() == null || productDto.getTaxRate().compareTo(BigDecimal.ZERO) < 0) {
				throw new UserIllegalArgumentException("Invalid tax rate",
						"Tax rate must be provided and cannot be negative", 400);
			}
			if (productDto.getWholesaleRate().compareTo(productDto.getRetailRate()) > 0) {
				throw new UserIllegalArgumentException("Invalid pricing",
						"Wholesale rate cannot be greater than retail rate", 400);
			}

			if (productDto.getOurPrice().compareTo(productDto.getWholesaleRate()) >= 0
					|| productDto.getOurPrice().compareTo(productDto.getRetailRate()) >= 0) {
				throw new UserIllegalArgumentException("Invalid our price",
						"Our price must be less than both wholesale and retail rates", 400);
			}
			if (productDto.getCategory() == null || productDto.getCategory().trim().isEmpty()) {
				throw new UserIllegalArgumentException("Missing category", "Product category is required", 400);
			}
				
			try {
			Product product = new Product();
	        product.setName(productDto.getName());
	        product.setProductNumber(productDto.getProductNumber());
	        product.setHsn(productDto.getHsn());
	        product.setDescription(productDto.getDescription());
	        product.setQuantity(productDto.getQuantity());
	        product.setOurPrice(productDto.getOurPrice());
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
			
		} catch (Exception e) {
			logger.error("Error saving product: {}", productDto, e);
			throw new RuntimeException("Error saving product", e);
		}
	}

	public boolean existsByName(String name) {
		return productRepository.existsByName(name);
	}

	public List<ProductDto> findAllProducts() {
	    List<Product> products = productRepository.findAll();

	
	    return products.stream()
                .map(this::mapToProductDto)
                .collect(Collectors.toList());
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
	        product.setHsn(productDto.getHsn());
	        product.setDescription(productDto.getDescription());
	        product.setQuantity(productDto.getQuantity());
	        product.setOurPrice(productDto.getOurPrice());
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

		} catch (Exception e) {
			logger.error("Error updating product: {}", productDto, e);
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
		productDto.setHsn(product.getHsn());
		productDto.setDescription(product.getDescription());
		productDto.setQuantity(product.getQuantity());
		productDto.setOurPrice(product.getOurPrice());
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

			User user = shop.getOwnerId();
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

}
