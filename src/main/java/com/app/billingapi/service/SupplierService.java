package com.app.billingapi.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.billingapi.dto.SupplierDto;
import com.app.billingapi.entity.Shop;
import com.app.billingapi.entity.Supplier;
import com.app.billingapi.exception.UserIllegalArgumentException;
import com.app.billingapi.exception.UserNotFoundException;
import com.app.billingapi.repository.ShopRepository;
import com.app.billingapi.repository.SupplierRepository;
import com.app.billingapi.util.JwtUtils;

@Service
public class SupplierService {

	@Autowired
	private SupplierRepository supplierRepository;

	@Autowired
	private ShopRepository shopRepository;

	@Autowired
	private JwtUtils jwtUtils;

	private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);

	public SupplierDto addSupplier(SupplierDto supplierDto, String token) {

		Long shopId = jwtUtils.extractShopId(token);

		logger.info("Saving  Customers");

		if (supplierDto.getName() == null || supplierDto.getName().trim().isEmpty()) {
			throw new UserIllegalArgumentException("Missing Supplier name", "Supplier name is required", 400);
		}

		if (supplierDto.getAddress() == null || supplierDto.getAddress().trim().isEmpty()) {
			throw new UserIllegalArgumentException("Missing place", "Supplier place is required", 400);
		}

		if (supplierDto.getPhone() == null || supplierDto.getPhone().toString().trim().isEmpty()) {
			throw new UserIllegalArgumentException("Missing phone number", "Supplier phone number is required", 400);
		}
		if (!supplierDto.getPhone().toString().matches("^[0-9]{10}$")) {
			throw new UserIllegalArgumentException("Invalid phone number",
					"Phone number must contain exactly 10 digits", 400);
		}

		try {

			Shop shop = shopRepository.findById(shopId)
					.orElseThrow(() -> new IllegalArgumentException("Invalid shop ID: " + supplierDto.getShopId()));

			Supplier supplier = new Supplier();

			supplier.setName(supplierDto.getName());
			supplier.setEmail(supplierDto.getEmail());
			supplier.setPhone(supplierDto.getPhone());
			supplier.setAddress(supplierDto.getAddress());
			supplier.setCity(supplierDto.getCity());
			supplier.setPincode(supplierDto.getPincode());
			supplier.setState(supplierDto.getState());
			supplier.setGstNumber(supplierDto.getGstNumber());

			supplier.setShopId(shop);
			
			Supplier savedSupplier = supplierRepository.save(supplier);
			return mapToSupplierDto(savedSupplier);

		} catch (Exception e) {
			logger.error("Error occurred while saving  Suppliers", e);
			throw new RuntimeException("Failed to save  Suppliers", e);
		}
	}

	public boolean existsByPhone(String phone) {
		return supplierRepository.existsByPhone(phone);
	}

	public List<SupplierDto> getAllSuppliers(Long shopId) {

		logger.info("Finding all Suppliers");

		try {

			Shop shop = shopRepository.findById(shopId)
					.orElseThrow(() -> new IllegalArgumentException("Invalid shop ID: " + shopId));

			return supplierRepository.findByShopId(shop).stream().map(this::mapToSupplierDto).toList();

		} catch (Exception e) {
			logger.error("Error finding all Suppliers", e);
			throw new RuntimeException("Error finding Suppliers", e);
		}
	}

	public SupplierDto getSupplierById(Long id) {
		return supplierRepository.findById(id).map(this::mapToSupplierDto)
				.orElseThrow(() -> new UserNotFoundException("Supplier not found with ID:", "Check id", 400));
	}

	public SupplierDto updateSupplier(Long id, SupplierDto supplierDto) {

		logger.info("Updating customer with ID: {}", supplierDto.getSupplierId());

		Supplier supplier = supplierRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Supplier not found with ID: " + id));

		supplier.setName(supplierDto.getName());
		supplier.setEmail(supplierDto.getEmail());
		supplier.setPhone(supplierDto.getPhone());
		supplier.setAddress(supplierDto.getAddress());
		supplier.setCity(supplierDto.getCity());
		supplier.setPincode(supplierDto.getPincode());
		supplier.setState(supplierDto.getState());
		supplier.setGstNumber(supplierDto.getGstNumber());

		Supplier suppliers = supplierRepository.save(supplier);

		return mapToSupplierDto(suppliers);
	}

	public SupplierDto deleteSupplier(Long supplierId) {

		logger.info("Deleting customer with ID: {}", supplierId);

		Supplier supplier = supplierRepository.findById(supplierId)
				.orElseThrow(() -> new IllegalArgumentException("Supplier not found with ID: " + supplierId));
		supplierRepository.delete(supplier);
		return null;
	}

	private SupplierDto mapToSupplierDto(Supplier supplier) {

		SupplierDto supplierDto = new SupplierDto();

		supplierDto.setSupplierId(supplier.getSupplierId());
		supplierDto.setName(supplier.getName());
		supplierDto.setEmail(supplier.getEmail());
		supplierDto.setPhone(supplier.getPhone());
		supplierDto.setAddress(supplier.getAddress());
		supplierDto.setCity(supplier.getCity());
		supplierDto.setPincode(supplier.getPincode());
		supplierDto.setState(supplier.getState());
		supplierDto.setGstNumber(supplier.getGstNumber());

		if (supplier.getShopId() != null) {
			supplierDto.setShopId(supplier.getShopId().getShopId());
		}

		return supplierDto;
	}
}
