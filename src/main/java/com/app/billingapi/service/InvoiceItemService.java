package com.app.billingapi.service;

import com.app.billingapi.dto.InvoiceItemDto;
import com.app.billingapi.entity.Invoice;
import com.app.billingapi.entity.InvoiceItem;
import com.app.billingapi.entity.Product;
import com.app.billingapi.exception.InvoiceNotFoundException;
import com.app.billingapi.repository.InvoiceItemRepository;
import com.app.billingapi.repository.InvoiceRepository;
import com.app.billingapi.repository.ProductRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvoiceItemService {

    private static final Logger logger = LoggerFactory.getLogger(InvoiceItemService.class);

    private final InvoiceItemRepository invoiceItemRepository;
    private final InvoiceRepository invoiceRepository;
    private final ProductRepository productRepository;

    public InvoiceItemService(InvoiceItemRepository invoiceItemRepository,
                              InvoiceRepository invoiceRepository,
                              ProductRepository productRepository) {
        this.invoiceItemRepository = invoiceItemRepository;
        this.invoiceRepository = invoiceRepository;
        this.productRepository = productRepository;
    }

    public InvoiceItem addInvoiceItem(InvoiceItemDto dto) {
        logger.info("Adding new invoice item");

        Invoice invoice = invoiceRepository.findById(dto.getInvoiceId())
                .orElseThrow(() -> new InvoiceNotFoundException("Invoice not found with ID: " + dto.getInvoiceId(), "Invalid ID", 404)); 

        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new InvoiceNotFoundException("Product not found with ID: " + dto.getProductId(),"Invalid ID", 404));

        InvoiceItem item = new InvoiceItem();
        item.setInvoice(invoice);
        item.setProduct(product);
        item.setQuantity(dto.getQuantity());
        item.setPrice(dto.getPrice());
        item.setTax(dto.getTax());

        BigDecimal total = dto.getPrice()
                .multiply(dto.getQuantity())
                .add(dto.getTax() != null ? dto.getTax() : BigDecimal.ZERO);

        item.setTotal(total);

        return invoiceItemRepository.save(item);
    }

    public List<InvoiceItemDto> getAllInvoiceItems() {
        logger.info("Fetching all invoice items");
        return invoiceItemRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public InvoiceItemDto getInvoiceItemById(Long id) {
        InvoiceItem item = invoiceItemRepository.findById(id)
                .orElseThrow(() -> new InvoiceNotFoundException("Invoice item not found with ID: " + id,"Invalid ID", 404));
        return mapToDto(item);
    }

    public InvoiceItem updateInvoiceItem(Long id, InvoiceItemDto dto) {
        
    	logger.info("Updating invoice item with ID: {}", id);

        InvoiceItem item = invoiceItemRepository.findById(id)
                .orElseThrow(() -> new InvoiceNotFoundException("Invoice item not found with ID: " + id,"Invalid ID", 404));

        Invoice invoice = invoiceRepository.findById(dto.getInvoiceId())
                .orElseThrow(() -> new InvoiceNotFoundException("Invoice not found with ID: " + dto.getInvoiceId(),"Invalid ID", 404));
        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new InvoiceNotFoundException("Product not found with ID: " + dto.getProductId(),"Invalid ID", 404));

        item.setInvoice(invoice);
        item.setProduct(product);
        item.setQuantity(dto.getQuantity());
        item.setPrice(dto.getPrice());
        item.setTax(dto.getTax());

        BigDecimal total = dto.getPrice()
                .multiply(dto.getQuantity())
                .add(dto.getTax() != null ? dto.getTax() : BigDecimal.ZERO);

        item.setTotal(total);

        return invoiceItemRepository.save(item);
    }

    public void deleteInvoiceItem(Long id) {
        logger.info("Deleting invoice item with ID: {}", id);
        InvoiceItem item = invoiceItemRepository.findById(id)
                .orElseThrow(() -> new InvoiceNotFoundException("Invoice item not found with ID: " + id,"Invalid ID", 404));
        invoiceItemRepository.delete(item);
    }

    private InvoiceItemDto mapToDto(InvoiceItem item) {
        InvoiceItemDto dto = new InvoiceItemDto();
        dto.setInvoiceItemId(item.getInvoiceItemId());
        dto.setInvoiceId(item.getInvoice().getInvoiceId());
        dto.setProductId(item.getProduct().getProductId());
        dto.setQuantity(item.getQuantity());
        dto.setPrice(item.getPrice());
        dto.setTax(item.getTax());
        dto.setTotal(item.getTotal());
        return dto;
    }
}
