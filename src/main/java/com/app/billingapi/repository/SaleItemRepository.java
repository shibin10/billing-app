package com.app.billingapi.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.app.billingapi.entity.SaleItem;

@Repository
public interface SaleItemRepository extends JpaRepository<SaleItem, Long> {
	List<SaleItem> findBySaleId_SaleId(Long saleId);

	List<SaleItem> findBySaleId_ShopId_ShopIdAndSaleId_SaleDateBetween(Long shopId, LocalDate start, LocalDate end);

	List<SaleItem> findByProduct_ProductId(Long productId);

	List<SaleItem> findBySaleId_ShopId_ShopId(Long shopId);
	
	List<SaleItem> findDistinctBySaleId_ShopId_ShopIdAndSaleId_SaleDateBetween(Long shopId, LocalDate start, LocalDate end);

	List<SaleItem> findBySaleId_SaleIdIn(List<Long> saleIds);

}
