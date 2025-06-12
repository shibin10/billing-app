package com.app.billingapi.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.app.billingapi.dto.CustomerDto;
import com.app.billingapi.dto.ShopDto;
import com.app.billingapi.dto.SignupUserDto;
import com.app.billingapi.entity.Customer;
import com.app.billingapi.entity.Shop;
import com.app.billingapi.entity.User;
import com.app.billingapi.exception.CustomerNotFoundException;
import com.app.billingapi.exception.UserIllegalArgumentException;
import com.app.billingapi.repository.CustomerRepository;
import com.app.billingapi.repository.ShopRepository;
import com.app.billingapi.repository.UserRepository;

import jakarta.validation.Valid;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {

	@Autowired
	private final CustomerRepository customerRepository;
	private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);
	private final ShopRepository shopRepository;
	private final UserRepository userRepository;

	public CustomerService(CustomerRepository customerRepository, ShopRepository shopRepository,
			UserRepository userRepository) {
		this.customerRepository = customerRepository;
		this.shopRepository = shopRepository;
		this.userRepository = userRepository;
	}

	public boolean existsByPlace(String place) {
		return customerRepository.existsByPlace(place);
	}

	public Customer saveCustomer(CustomerDto customerDto) {
		logger.info("Saving  Customers");
		
		 

		    if (customerDto.getName() == null || customerDto.getName().trim().isEmpty()) {
		        throw new UserIllegalArgumentException("Missing customer name", "Customer name is required", 400);
		    }

		    if (customerDto.getPlace() == null || customerDto.getPlace().trim().isEmpty()) {
		        throw new UserIllegalArgumentException("Missing place", "Customer place is required", 400);
		    }

		    if (customerDto.getPhone() == null || customerDto.getPhone().toString().trim().isEmpty()) {
		        throw new UserIllegalArgumentException("Missing phone number", "Customer phone number is required", 400);
		    }
		    if (!customerDto.getPhone().toString().matches("^[0-9]{10}$")) {
		        throw new UserIllegalArgumentException("Invalid phone number", "Phone number must contain exactly 10 digits", 400);
		    }


		try {

			String loggedInUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
			User user = userRepository.findByEmail(loggedInUserEmail)
					.orElseThrow(() -> new IllegalArgumentException("User not found: " + loggedInUserEmail));
			
			Long userId = user.getUserId();

			Shop shop = shopRepository.findById(customerDto.getShopId())
					.orElseThrow(() -> new IllegalArgumentException("Invalid Shop Id: " + customerDto.getShopId()));


			Customer customer = new Customer();
			customer.setName(customerDto.getName());
			customer.setPlace(customerDto.getPlace());
			customer.setPhone(customerDto.getPhone());
			customer.setShopId(shop);

			return customerRepository.save(customer);
		} catch (Exception e) {
			logger.error("Error occurred while saving  Customer", e);
			throw new RuntimeException("Failed to save  Customer", e);
		}
	}

	public List<CustomerDto> findAllCustomers() {
		logger.info("Finding all Customers");
		try {
			List<Customer> customers = customerRepository.findAll();
			return customers.stream().map((customer) -> mapToCustomertDto(customer)).collect(Collectors.toList());
		} catch (Exception e) {
			logger.error("Error finding all shops", e);
			throw new RuntimeException("Error finding shops", e);
		}
	}

	public CustomerDto updateCustomer(CustomerDto customerDto) {
		logger.info("Updating customer with ID: {}", customerDto.getCustomerId());
		
		Customer customer = customerRepository.findById(customerDto.getCustomerId())
				.orElseThrow(() -> new CustomerNotFoundException(
						"Customer not found with ID: " + customerDto.getCustomerId(),
						"Please provide valid customer details.", 404));

		customer.setName(customerDto.getName());
		customer.setPlace(customerDto.getPlace());
		customer.setPhone(customerDto.getPhone());

		Customer customers = customerRepository.save(customer);
		
		return mapToCustomertDto(customers);
	}

	public CustomerDto findCustomerById(Long customerId) {
		return customerRepository.findById(customerId).map(this::mapToCustomertDto).orElse(null);

	}

	public CustomerDto deleteCustomerById(@Valid Long customerId) {
		logger.info("Deleting customer with ID: {}", customerId);
		Customer customer = customerRepository.findById(customerId)
				.orElseThrow(() -> new CustomerNotFoundException("Customer not found with ID: " + customerId,
						"Please check the ID and try again.", 404));

		customerRepository.delete(customer);
		logger.info("Customer with ID: {} deleted successfully", customerId);
		return null;
	}

	public List<CustomerDto> findCustomersByPlace(String place) {
		List<Customer> customer = customerRepository.findByPlace(place);
		return customer.stream().map(this::mapToCustomertDto).collect(Collectors.toList());
	}

	private CustomerDto mapToCustomertDto(Customer customer) {
		CustomerDto customerDto = new CustomerDto();
		customerDto.setName(customer.getName());
		customerDto.setPlace(customer.getPlace());
		customerDto.setPhone(customer.getPhone());

		Shop shop = customer.getShopId();
		if (shop != null) {
			ShopDto shopDto = new ShopDto();
			shopDto.setShopId(shop.getShopId());
			shopDto.setName(shop.getName());
			shopDto.setPlace(shop.getPlace());
			shopDto.setStatus(shop.getStatus());
			shopDto.setMap(shop.getMap());
			customerDto.setShop(shopDto);

		User user = shop.getOwnerId();
		if (user != null) {
			SignupUserDto userDto = new SignupUserDto();
			userDto.setUserId(user.getUserId());
			userDto.setFullName(user.getFullName());
			userDto.setEmail(user.getEmail());
			userDto.setPlace(user.getPlace());
			userDto.setPhone(user.getPhone());
			userDto.setStatus(user.getStatus());
			shopDto.setOwner(userDto);
		}
		

	}
		return customerDto;
	}

	public boolean existsByPhone(Integer phone) {
		// TODO Auto-generated method stub
		return false;
	}
}
