package com.app.billingapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.billingapi.entity.Shop;
import com.app.billingapi.entity.Supplier;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {
	
	boolean existsByNameIgnoreCaseAndShopId(String name, Shop shop);
	boolean existsByPhone(String phone);

	List<Supplier> findByShopId(Shop shop);
}