package com.app.billingapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.billingapi.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByEmail(String email);

	boolean existsByPhone(String phone);

	Optional<User> findByPhone(String phone);
	
	List<User> findByShops_ShopId(Long shopId);


	
}
