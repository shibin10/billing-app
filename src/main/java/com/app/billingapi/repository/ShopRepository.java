package com.app.billingapi.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.app.billingapi.entity.Shop;

public interface ShopRepository extends JpaRepository<Shop, Long> {



	boolean existsById(Long shopId);

}
