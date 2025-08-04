package com.app.billingapi.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.app.billingapi.entity.Sale;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {

	List<Sale> findByShopId_ShopIdAndSaleDateBetween(Long shopId, LocalDate from, LocalDate to);

    List<Sale> findByShopId_ShopId(Long shopId);
  //  List<Sale> findByShopIdAndSaleDateBetween(Long shopId, LocalDate startDate, LocalDate endDate);

    
}

