package com.app.billingapi.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.app.billingapi.dto.ShopDto;
import com.app.billingapi.dto.SignupUserDto;
import com.app.billingapi.dto.SubscriptionPlanDto;
import com.app.billingapi.entity.Role;
import com.app.billingapi.entity.Shop;
import com.app.billingapi.entity.SubscriptionPlan;
import com.app.billingapi.entity.User;
import com.app.billingapi.enums.ShopStatus;
import com.app.billingapi.repository.ShopRepository;
import com.app.billingapi.repository.SubscriptionPlanRepository;
import com.app.billingapi.repository.UserRepository;
import com.app.billingapi.util.JwtUtils;


@Service
public class ShopService {

	private final SubscriptionPlanRepository subscriptionPlanRepository;

	private final ShopRepository shopRepository;
	private static final Logger logger = LoggerFactory.getLogger(ShopService.class);

	private final UserRepository userRepository;
	private final JwtUtils jwtUtils;
	private final FileStorageService fileStorageService;

	public ShopService(ShopRepository shopRepository, UserRepository userRepository,
			SubscriptionPlanRepository subscriptionPlanRepository, FileStorageService fileStorageService,JwtUtils jwtUtils) {
		this.shopRepository = shopRepository;
		this.userRepository = userRepository;
		this.subscriptionPlanRepository = subscriptionPlanRepository;
		this.fileStorageService = fileStorageService;
		this.jwtUtils = jwtUtils;
	}

	public ShopDto saveShop(ShopDto shopDto) {
		logger.info("Admin saving shop: {}", shopDto);

		try {
			Long ownerId = shopDto.getOwnerId();
			if (ownerId == null) {
				throw new RuntimeException("ownerId is required");
			}

			User user = userRepository.findById(ownerId)
					.orElseThrow(() -> new RuntimeException("User not found with ID: " + ownerId));

			boolean isEligible = user.getRoles().stream().map(Role::getRoleName)
					.anyMatch(role -> role.equals("ROLE_OWNER"));

			if (!isEligible) {
				throw new RuntimeException("User is not OWNER");
			}

			Long planId = shopDto.getSubscriptionPlan().getPlanId();
			SubscriptionPlan plan = subscriptionPlanRepository.findById(planId)
					.orElseThrow(() -> new RuntimeException("Subscription plan not found with ID: " + planId));

			Shop shop = new Shop();
			shop.setName(shopDto.getName());
			shop.setDescription(shopDto.getDescription());
			shop.setPlace(shopDto.getPlace());
			shop.setAddress(shopDto.getAddress());
			shop.setStatus(ShopStatus.CREATED);
			shop.setMap(shopDto.getMap());
			shop.setLogo(shopDto.getLogo());
			shop.setPhone(shopDto.getPhone());
			shop.setGstNo(shopDto.getGstNo());
			shop.setSubscriptionPlanId(plan);
			shop.setOwner(user);
			shop.getUsers().add(user);

	/*		if (imageFile != null && !imageFile.isEmpty()) {
				String relativePath = fileStorageService.saveImage(imageFile, "shops"); // stores at
																						// /uploads/shops/uuid.ext
				shop.setLogo(relativePath); // persist the relative path
			}*/

			shopRepository.save(shop);
			user.getShops().add(shop);
			userRepository.save(user);

			return mapToShopDto(shop);

		} catch (Exception e) {
			logger.error("Error saving shop by admin: {}", shopDto, e);
			throw new RuntimeException("Error saving shop", e);
		}
	}

	public ShopDto findShopById(Long shopId) {
		logger.info("Finding shop by ID: {}", shopId);
		try {
			return shopRepository.findById(shopId).map(this::mapToShopDto).orElse(null);
		} catch (Exception e) {
			logger.error("Error finding shop by ID: {}", shopId, e);
			throw new RuntimeException("Error finding shop", e);
		}
	}

	public List<ShopDto> findAllShops(String token) {

		String role = jwtUtils.extractRoleName(token);
	//	System.out.println(role);
		String loggedInEmail = SecurityContextHolder.getContext().getAuthentication().getName();
		boolean ownerOrStaff = role.equals("ROLE_OWNER") || role.equals("ROLE_STAFF");

		if (ownerOrStaff) {

			Long shopId = jwtUtils.extractShopId(token); 
			Shop shop = shopRepository.findById(shopId)
	                .orElseThrow(() -> new IllegalArgumentException("Shop not found with ID: " + shopId));
			  return Collections.singletonList(mapToShopDto(shop));
		}

		return shopRepository.findAll().stream().map(this::mapToShopDto).collect(Collectors.toList());
	}

	public ShopDto updateShop(ShopDto shopDto) {
		try {
			logger.info("Updating shop: {}", shopDto);

			Long userId = shopDto.getOwner().getUserId();
			User user = userRepository.findById(userId)
					.orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

			Long planId = shopDto.getSubscriptionPlan().getPlanId();
			SubscriptionPlan plan = subscriptionPlanRepository.findById(planId)
					.orElseThrow(() -> new RuntimeException("Subscription plan not found with ID: " + planId));

			Optional<Shop> optionalShop = shopRepository.findById(shopDto.getShopId());

			if (optionalShop.isPresent()) {
				Shop shop = optionalShop.get();

				shop.setName(shopDto.getName());
				shop.setDescription(shopDto.getDescription());
				shop.setPlace(shopDto.getPlace());
				shop.setAddress(shopDto.getAddress());
				shop.setStatus(shopDto.getStatus());
				shop.setMap(shopDto.getMap());
				shop.setPhone(shopDto.getPhone());
				shop.setGstNo(shopDto.getGstNo());
				shop.setLogo(shopDto.getLogo());
				shop.setOwner(user);
				shop.setSubscriptionPlanId(plan);

				Shop shops = shopRepository.save(shop);
				return mapToShopDto(shops);

			} else {
				logger.warn("Shop not found with ID: {}", shopDto.getShopId());
				throw new RuntimeException("Shop not found with id " + shopDto.getShopId());
			}
		} catch (Exception e) {
			logger.error("Error updating shop: {}", shopDto, e);
			throw new RuntimeException("Error updating shop", e);
		}

	}

	public Shop deleteShopById(Long shopId) {
		logger.info("Deleting shop by ID: {}", shopId);
		try {
			Optional<Shop> optionalShop = shopRepository.findById(shopId);
			if (optionalShop.isPresent()) {
				Shop shop = shopRepository.findById(shopId)
						.orElseThrow(() -> new RuntimeException("Shop not found with ID: " + shopId));
				shop.setStatus(ShopStatus.INACTIVE);
				return shopRepository.save(shop);
			} else {
				logger.warn("Shop not found for deletion with ID: {}", shopId);
			}
			return null;
		} catch (Exception e) {
			logger.error("Error deleting shop by ID: {}", shopId, e);
			throw new RuntimeException("Error deleting shop", e);
		}

	}

	private ShopDto mapToShopDto(Shop shop) {

		ShopDto shopDto = new ShopDto();
		shopDto.setShopId(shop.getShopId());
		shopDto.setName(shop.getName());
		shopDto.setDescription(shop.getDescription());
		shopDto.setPhone(shop.getPhone());
		shopDto.setAddress(shop.getAddress());
		shopDto.setPlace(shop.getPlace());
		shopDto.setStatus(shop.getStatus());
		shopDto.setMap(shop.getMap());
		shopDto.setGstNo(shop.getGstNo());
		shopDto.setLogo(shop.getLogo());

		User user = shop.getOwner();
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
		SubscriptionPlan plan = shop.getSubscriptionPlanId();
		if (plan != null) {
			SubscriptionPlanDto subscriptionPlanDto = new SubscriptionPlanDto();

			subscriptionPlanDto.setPlanId(plan.getPlanId());
			subscriptionPlanDto.setPlanName(plan.getPlanName());
			shopDto.setSubscriptionPlan(subscriptionPlanDto);
		}

		return shopDto;
	}

}