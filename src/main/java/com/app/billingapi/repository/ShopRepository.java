package com.app.billingapi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.billingapi.entity.Shop;
import com.app.billingapi.entity.User;

public interface ShopRepository extends JpaRepository<Shop, Long> {

	boolean existsByShopId(Long shopId);

	Optional<Shop> findByOwner(User user);

	List<Shop> findByOwner_UserId(Long ownerId);

	Optional<Shop> findByShopId(Long shopId);
	//Optional<Shop> findByOwnerId_UserId(Long userId);





}
