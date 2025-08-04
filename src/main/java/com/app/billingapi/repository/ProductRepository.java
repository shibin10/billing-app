package com.app.billingapi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.billingapi.entity.Product;
import com.app.billingapi.entity.Shop;

public interface ProductRepository extends JpaRepository<Product, Long> {
	boolean existsByName(String name);

	List<Product> findByShopId_ShopId(Long shopId);

	Optional<Product> findByNameAndShopId_ShopId(String name, Long shopId);

	List<Product> findByShopId_ShopIdAndProductIdNotIn(Long shopId, List<Long> excludedProductIds);

	long countByShopId_ShopId(Long shopId);
	
	boolean existsByProductNumberIgnoreCaseAndShopId(String productNumber, Shop shopId);



}
