package com.app.billingapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.billingapi.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
	boolean existsByPlace(String place);

	List<Customer> findByPlace(String place);

	//   Optional<Customer> findByShopId_UserId(Long userId);
}
