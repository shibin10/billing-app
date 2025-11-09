package com.app.billingapi.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.billingapi.entity.Customer;
import com.app.billingapi.entity.Invoice;
import com.app.billingapi.entity.Sale;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

	int countByInvoiceDate(LocalDate invoiceDate);
	List<Invoice> findByShopId_ShopIdAndInvoiceDateBetween(Long shopId, LocalDate from, LocalDate to);
    List<Invoice> findByShopId_ShopId(Long shopId);
    Invoice findTopByOrderByInvoiceIdDesc();
    List<Invoice> findByCustomerId(Customer customer);
    List<Invoice> findByCustomerIdAndInvoiceDateBetween(Customer customer, LocalDate fromDate, LocalDate toDate);
    List<Invoice> findBySalesId_SaleItems_Product_ProductNumber(String productNumber);
    Optional<Invoice> findBySalesId(Sale sale);

}
