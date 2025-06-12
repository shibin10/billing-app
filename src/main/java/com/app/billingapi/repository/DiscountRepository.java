package com.app.billingapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.app.billingapi.entity.Discount;

@Repository
public interface DiscountRepository extends JpaRepository<Discount, Long> {
}
