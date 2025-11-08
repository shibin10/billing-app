package com.app.billingapi.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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
	/*
	@Query(value = """
		     SELECT p.name as productName,
		           SUM(si.quantity) as quantity,
		           SUM(si.price * si.quantity) as subTotal,
		           SUM(si.tax) as tax,
		           SUM(si.discount) as discount,
		            SUM((si.price * si.quantity) + si.tax - si.discount) AS finalAmount
		    FROM sale_item si
		    JOIN product p ON si.productid = p.productid
		    JOIN sale s ON si.saleid = s.saleid
		    WHERE s.customerid = 1
		    GROUP BY p.name
		    ORDER BY finalAmount DESC
	
		""", nativeQuery = true)
		List<Object[]> findTopProductsByCustomer(@Param("customerId") Long customerId);
	
	@Query(value = """
		    SELECT p.name AS productName,
		           SUM(si.quantity) AS totalQuantity,
		           SUM(si.price * si.quantity) AS subTotal,
		           SUM(si.tax) AS totalTax,
		           SUM(si.discount) AS totalDiscount,
		           SUM((si.price * si.quantity) + si.tax - si.discount) AS finalAmount,
		           i.invoice_date AS invoiceDate
		    FROM sale_item si
		    JOIN product p ON si.productid = p.productid
		    JOIN sale s ON si.saleid = s.saleid
		    JOIN invoice i ON i.saleid = s.saleid
		    WHERE s.customerid = :customerId
		      AND i.invoice_date BETWEEN :fromDate AND :toDate
		    GROUP BY p.name, i.invoice_date
		    ORDER BY totalQuantity DESC
		""", nativeQuery = true)
		List<Object[]> findTopProductsByCustomerAndDateRange(
		        @Param("customerId") Long customerId,
		        @Param("fromDate") LocalDate fromDate,
		        @Param("toDate") LocalDate toDate);*/

	@Query(value = """
		    SELECT p.name AS productName,
		           SUM(si.quantity) AS totalQuantity,
		           SUM(si.price * si.quantity) AS subTotal,
		           SUM(si.tax) AS totalTax,
		           SUM(si.discount) AS totalDiscount,
		           SUM((si.price * si.quantity) + si.tax - si.discount) AS finalAmount,
		           i.invoice_date AS invoiceDate
		    FROM sale_item si
		    JOIN product p ON si.productid = p.productid
		    JOIN sale s ON si.saleid = s.saleid
		    JOIN invoice i ON i.saleid = s.saleid
		    WHERE s.customerid = :customerId
		      AND i.invoice_date BETWEEN :fromDate AND :toDate
		    GROUP BY p.name, i.invoice_date
		    ORDER BY totalQuantity DESC
		""", nativeQuery = true)
		List<Object[]> findTopProductsByCustomerAndDateRange(
		        @Param("customerId") Long customerId,
		        @Param("fromDate") LocalDate fromDate,
		        @Param("toDate") LocalDate toDate);



		@Query(value = "SELECT p.hsn, p.name,p.taxrate, " +
		        "SUM(si.quantity) AS totalQuantity, " +
		        "SUM(si.quantity * p.retailrate) AS totalAmount, " +
		        "SUM(si.tax) AS totalTax, " +
		        "(SUM(si.quantity * p.retailrate) + SUM(si.tax)) AS finalAmount " +
		        "FROM sale_item si " +
		        "JOIN product p ON si.productid = p.productid " +
		        "JOIN sale s ON si.saleid = s.saleid " +
		        "WHERE p.hsn = :hsn AND s.sale_date BETWEEN :fromDate AND :toDate " +
		        "GROUP BY p.hsn, p.name, p.taxrate",
		        nativeQuery = true)
		List<Object[]> getSalesReportByHsnAndDate(
		        @Param("hsn") String hsn,
		        @Param("fromDate") String fromDate,
		        @Param("toDate") String toDate
		);

		@Query(value = "SELECT p.hsn, p.name, p.taxrate, " +
		        "SUM(si.quantity) AS totalQuantity, " +
		        "SUM(si.quantity * p.retailrate) AS totalAmount, " +
		        "SUM(si.tax) AS totalTax, " +
		        "(SUM(si.quantity * p.retailrate) + SUM(si.tax)) AS finalAmount " +
		        "FROM sale_item si " +
		        "JOIN product p ON si.productid = p.productid " +
		        "JOIN sale s ON si.saleid = s.saleid " +
		        "WHERE s.sale_date BETWEEN :fromDate AND :toDate " +
		        "GROUP BY p.hsn, p.name, p.taxrate",
		        nativeQuery = true)
		List<Object[]> getSalesReportForAllProductsByDate(
		        @Param("fromDate") String fromDate,
		        @Param("toDate") String toDate
		);



}
