package com.app.billingapi.service;

import com.app.billingapi.dto.SaleItemDto;
import com.app.billingapi.entity.Product;
import com.app.billingapi.entity.Sale;
import com.app.billingapi.entity.SaleItem;
import com.app.billingapi.repository.ProductRepository;
import com.app.billingapi.repository.SaleItemRepository;
import com.app.billingapi.repository.SaleRepository;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SaleItemService {

	private static final Logger logger = LoggerFactory.getLogger(SaleItemService.class);

	private final SaleItemRepository saleItemRepository;
	private final SaleRepository saleRepository;
	private final ProductRepository productRepository;

	public SaleItemService(SaleItemRepository saleItemRepository,
	                       SaleRepository saleRepository,
	                       ProductRepository productRepository) {
		this.saleItemRepository = saleItemRepository;
		this.saleRepository = saleRepository;
		this.productRepository = productRepository;
	}

	public SaleItem createSaleItem(SaleItemDto dto) {
		logger.info("Creating SaleItem...");

		Sale sale = saleRepository.findById(dto.getSaleId())
				.orElseThrow(() -> new IllegalArgumentException("Invalid sale ID: " + dto.getSaleId()));

		Product product = productRepository.findById(dto.getProductId())
				.orElseThrow(() -> new IllegalArgumentException("Invalid product ID: " + dto.getProductId()));

		SaleItem item = new SaleItem();
		item.setSaleId(sale);
		item.setProduct(product);
		item.setQuantity(dto.getQuantity());
		item.setPrice(dto.getPrice());
		item.setTax(dto.getTax());
		item.setTotal(dto.getTotal());

		return saleItemRepository.save(item);
	}

	public List<SaleItemDto> getAllSaleItems() {
		return saleItemRepository.findAll().stream().map(this::mapToDto).collect(Collectors.toList());
	}

	public SaleItemDto getSaleItemById(Long id) {
		SaleItem item = saleItemRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("SaleItem not found with ID: " + id));
		return mapToDto(item);
	}

	public SaleItemDto updateSaleItem(SaleItemDto dto) {
		SaleItem item = saleItemRepository.findById(dto.getSaleItemId())
				.orElseThrow(() -> new IllegalArgumentException("SaleItem not found with ID: " + dto.getSaleItemId()));

		if (dto.getSaleId() != null) {
			Sale sale = saleRepository.findById(dto.getSaleId())
					.orElseThrow(() -> new IllegalArgumentException("Invalid sale ID"));
			item.setSaleId(sale);
		}

		if (dto.getProductId() != null) {
			Product product = productRepository.findById(dto.getProductId())
					.orElseThrow(() -> new IllegalArgumentException("Invalid product ID"));
			item.setProduct(product);
		}

		item.setQuantity(dto.getQuantity());
		item.setPrice(dto.getPrice());
		item.setTax(dto.getTax());
		item.setTotal(dto.getTotal());

		return mapToDto(saleItemRepository.save(item));
	}

	public void deleteSaleItem(Long id) {
		SaleItem item = saleItemRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("SaleItem not found with ID: " + id));
		saleItemRepository.delete(item);
	}

	private SaleItemDto mapToDto(SaleItem item) {
		SaleItemDto dto = new SaleItemDto();
		dto.setSaleItemId(item.getInvoiceItemId());
		dto.setSaleId(item.getSaleId() != null ? item.getSaleId().getSaleId() : null);
		dto.setProductId(item.getProduct() != null ? item.getProduct().getProductId() : null);
		dto.setQuantity(item.getQuantity());
		dto.setPrice(item.getPrice());
		dto.setTax(item.getTax());
		dto.setTotal(item.getTotal());
		return dto;
	}
}

