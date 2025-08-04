package com.app.billingapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.billingapi.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
	boolean existsByPhone(String phone);

	List<Customer> findByPlace(String place);

	List<Customer> findByShopId_ShopId(Long shopId);

}
