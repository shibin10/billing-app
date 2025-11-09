package com.app.billingapi.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.app.billingapi.dto.CustomerReportDto;
import com.app.billingapi.dto.TopProductDto;
import com.app.billingapi.entity.Customer;
import com.app.billingapi.entity.Invoice;
import com.app.billingapi.repository.CustomerRepository;
import com.app.billingapi.repository.InvoiceRepository;
import com.app.billingapi.repository.SaleItemRepository;

@Service
public class CustomerReportService {


    private final CustomerRepository customerRepository;
    private final InvoiceRepository invoiceRepository;
    private final SaleItemRepository saleItemRepository;

    public CustomerReportService(CustomerRepository customerRepository,
                                 InvoiceRepository invoiceRepository,
                                 SaleItemRepository saleItemRepository) {
        this.customerRepository = customerRepository;
        this.invoiceRepository = invoiceRepository;
        this.saleItemRepository = saleItemRepository;
    }

    public CustomerReportDto generateCustomerReport(Long customerId, LocalDate fromDate, LocalDate toDate) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid customer ID"));

        // Fetch invoices
        List<Invoice> invoices = invoiceRepository.findByCustomerIdAndInvoiceDateBetween(customer, fromDate, toDate);

        CustomerReportDto dto = new CustomerReportDto();

        // Basic info
        dto.setCustomerId(customer.getCustomerId());
        dto.setCustomerName(customer.getName());
        dto.setPhone(customer.getPhone());
        dto.setPlace(customer.getPlace());
        dto.setCustomerType(customer.getCustomerType().toString());

        if (invoices.isEmpty()) {
            dto.setTotalPurchases(BigDecimal.ZERO);
            dto.setTotalBills(0);
            dto.setAverageBillValue(BigDecimal.ZERO);
            dto.setBiggestBill(BigDecimal.ZERO);
            dto.setTotalDiscount(BigDecimal.ZERO);
            dto.setTotalPaid(BigDecimal.ZERO);
            dto.setPendingBalance(BigDecimal.ZERO);
            dto.setAdvancePaid(BigDecimal.ZERO);
            dto.setOverdueInvoices(0);
            dto.setTopProducts(new ArrayList<>());
            return dto;
        }

        // Sales summary
        BigDecimal totalPurchases = invoices.stream()
                .map(Invoice::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        int totalBills = invoices.size();

        BigDecimal biggestBill = invoices.stream()
                .map(Invoice::getTotalAmount)
                .max(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);

        BigDecimal avgBill = totalBills > 0
                ? totalPurchases.divide(BigDecimal.valueOf(totalBills), RoundingMode.HALF_UP)
                : BigDecimal.ZERO;

        BigDecimal totalDiscount = invoices.stream()
                .map(Invoice::getDiscount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalPaid = invoices.stream()
                .map(i -> i.getAmountPaid() != null ? i.getAmountPaid() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal pendingBalance = totalPurchases.subtract(totalPaid);

        // Top Products (native query with invoice date)
        List<Object[]> productData = saleItemRepository
                .findTopProductsByCustomerAndDateRange(customer.getCustomerId(), fromDate, toDate);

        List<TopProductDto> topProducts = productData.stream()
                .map(obj -> new TopProductDto(
                        (String) obj[0],                   // productName
                        (BigDecimal) obj[1],               // quantity
                        (BigDecimal) obj[2],               // subTotal
                        (BigDecimal) obj[3],               // tax
                        (BigDecimal) obj[4],               // discount
                        (BigDecimal) obj[5],               // finalAmount
                        obj[6] != null
                                ? ((java.sql.Date) obj[6]).toLocalDate()  // invoiceDate
                                : null
                ))
                .collect(Collectors.toList());

        // Set values in DTO
        dto.setTotalPurchases(totalPurchases);
        dto.setTotalBills(totalBills);
        dto.setAverageBillValue(avgBill);
        dto.setBiggestBill(biggestBill);
        dto.setTotalDiscount(totalDiscount);
        dto.setTotalPaid(totalPaid);
        dto.setPendingBalance(pendingBalance);
        dto.setAdvancePaid(BigDecimal.ZERO);
        dto.setTopProducts(topProducts);

        return dto;
    }


    public List<CustomerReportDto> generateAllCustomersReport(LocalDate fromDate, LocalDate toDate) {
        List<CustomerReportDto> reports = new ArrayList<>();

        try {
            // Fetch all customers
            List<Customer> customers = customerRepository.findAll();

            for (Customer customer : customers) {
                List<Invoice> invoices = invoiceRepository.findByCustomerIdAndInvoiceDateBetween(
                        customer, fromDate, toDate);

                CustomerReportDto dto = new CustomerReportDto();

                // Basic info
                dto.setCustomerId(customer.getCustomerId());
                dto.setCustomerName(customer.getName());
                dto.setPhone(customer.getPhone());
                dto.setPlace(customer.getPlace());
                dto.setCustomerType(customer.getCustomerType().toString());

                if (invoices.isEmpty()) {
                    dto.setTotalPurchases(BigDecimal.ZERO);
                    dto.setTotalBills(0);
                    dto.setAverageBillValue(BigDecimal.ZERO);
                    dto.setBiggestBill(BigDecimal.ZERO);
                    dto.setTotalDiscount(BigDecimal.ZERO);
                    dto.setTotalPaid(BigDecimal.ZERO);
                    dto.setPendingBalance(BigDecimal.ZERO);
                    dto.setAdvancePaid(BigDecimal.ZERO);
                    dto.setOverdueInvoices(0);
                    dto.setTopProducts(new ArrayList<>());
                } else {
                    // Sales summary
                    BigDecimal totalPurchases = invoices.stream()
                            .map(Invoice::getTotalAmount)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);

                    Integer totalBills = invoices.size();
                    BigDecimal biggestBill = invoices.stream()
                            .map(Invoice::getTotalAmount)
                            .max(BigDecimal::compareTo)
                            .orElse(BigDecimal.ZERO);

                    BigDecimal avgBill = totalBills > 0
                            ? totalPurchases.divide(BigDecimal.valueOf(totalBills), RoundingMode.HALF_UP)
                            : BigDecimal.ZERO;

                    BigDecimal totalDiscount = invoices.stream()
                            .map(Invoice::getDiscount)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);

                    BigDecimal totalPaid = invoices.stream()
                            .map(i -> i.getAmountPaid() != null ? i.getAmountPaid() : BigDecimal.ZERO)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);

                    BigDecimal pendingBalance = totalPurchases.subtract(totalPaid);

                    // Top Products (native query with invoice date)
                    List<Object[]> productData = saleItemRepository
                            .findTopProductsByCustomerAndDateRange(customer.getCustomerId(), fromDate, toDate);

                    List<TopProductDto> topProducts = productData.stream()
                            .map(obj -> new TopProductDto(
                                    (String) obj[0],                   // productName
                                    (BigDecimal) obj[1],               // quantity
                                    (BigDecimal) obj[2],               // subTotal
                                    (BigDecimal) obj[3],               // tax
                                    (BigDecimal) obj[4],               // discount
                                    (BigDecimal) obj[5],               // finalAmount
                                    obj[6] != null
                                            ? ((java.sql.Date) obj[6]).toLocalDate()  // invoiceDate
                                            : null
                            ))
                            .collect(Collectors.toList());

                    dto.setTotalPurchases(totalPurchases);
                    dto.setTotalBills(totalBills);
                    dto.setAverageBillValue(avgBill);
                    dto.setBiggestBill(biggestBill);
                    dto.setTotalDiscount(totalDiscount);
                    dto.setTotalPaid(totalPaid);
                    dto.setPendingBalance(pendingBalance);
                    dto.setAdvancePaid(BigDecimal.ZERO);
                    dto.setTopProducts(topProducts);
                }

                reports.add(dto);
            }

        } catch (Exception e) {
            e.printStackTrace(); // prints full stack trace
            throw new RuntimeException("Error while generating customer reports: " + e.getMessage(), e);
        }

        return reports;
    }


}

