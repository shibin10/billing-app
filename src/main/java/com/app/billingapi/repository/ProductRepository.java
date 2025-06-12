package com.app.billingapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.billingapi.entity.Product;

public interface ProductRepository  extends JpaRepository<Product, Long>{
boolean existsByName(String name);

}
