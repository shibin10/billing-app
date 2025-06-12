package com.app.billingapi.service;

import java.util.List;
import java.util.Optional;

import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.billingapi.dto.ShopDto;
import com.app.billingapi.dto.SignupUserDto;
import com.app.billingapi.dto.SubscriptionPlanDto;
import com.app.billingapi.entity.Shop;
import com.app.billingapi.entity.SubscriptionPlan;
import com.app.billingapi.entity.User;
import com.app.billingapi.enums.ShopStatus;
import com.app.billingapi.repository.ShopRepository;
import com.app.billingapi.repository.SubscriptionPlanRepository;
import com.app.billingapi.repository.UserRepository;

@Service
public class ShopService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	SubscriptionPlanRepository subscriptionPlanRepository;

	private final ShopRepository shopRepository;
	private static final Logger logger = LoggerFactory.getLogger(ShopService.class);

	public ShopService(ShopRepository shopRepository, UserRepository userRepository,
			SubscriptionPlanRepository subscriptionPlanRepository) {
		this.shopRepository = shopRepository;
		this.userRepository = userRepository;
		this.subscriptionPlanRepository = subscriptionPlanRepository;
	}

	public ShopDto saveShop(ShopDto shopDto) {
		logger.info("Saving shop: {}", shopDto);
		try {

			Long ownerId = shopDto.getOwnerId();
			if (ownerId == null)
				throw new RuntimeException("ownerId is required");

			User user = userRepository.findById(ownerId)
					.orElseThrow(() -> new RuntimeException("User not found with ID: " + ownerId));

			Long planId = shopDto.getSubscriptionPlan().getPlanId();
			SubscriptionPlan plan = subscriptionPlanRepository.findById(planId)
					.orElseThrow(() -> new RuntimeException("Subscription plan not found with ID: " + planId));

			Shop shop = new Shop();
			shop.setName(shopDto.getName());
			shop.setPlace(shopDto.getPlace());
			shop.setStatus(ShopStatus.CREATED);
			shop.setMap(shopDto.getMap());
			shop.setOwnerId(user);
			shop.setSubscriptionPlanId(plan);

			Shop save = shopRepository.save(shop);
			return mapToShopDto(save);
		} catch (Exception e) {
			logger.error("Error saving shop: {}", shopDto, e);
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

	public List<ShopDto> findAllShops() {
		logger.info("Finding all shops");
		try {
			List<Shop> shops = shopRepository.findAll();
			return shops.stream().map((shop) -> mapToShopDto(shop)).collect(Collectors.toList());
		} catch (Exception e) {
			logger.error("Error finding all shops", e);
			throw new RuntimeException("Error finding shops", e);
		}
	}

	public Shop updateShop(ShopDto shopDto) {
		try {
			logger.info("Updating shop: {}", shopDto);
			
			User user = userRepository.findById(shopDto.getOwner().getUserId()).orElseThrow(
					() -> new RuntimeException("User not found with ID: " + shopDto.getOwner().getUserId()));
			Optional<Shop> optionalShop = shopRepository.findById(shopDto.getShopId());

			if (optionalShop.isPresent()) {
				Shop shop = optionalShop.get();

				shop.setName(shopDto.getName());
				shop.setPlace(shopDto.getPlace());
				shop.setStatus(shopDto.getStatus());
				shop.setMap(shopDto.getMap());
				shop.setOwnerId(user);
				return shopRepository.save(shop);
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
		shopDto.setPlace(shop.getPlace());
		shopDto.setStatus(shop.getStatus());
		shopDto.setMap(shop.getMap());

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
		SubscriptionPlan plan = shop.getSubscriptionPlanId();
		if (plan != null) {
			SubscriptionPlanDto subscriptionPlanDto = new SubscriptionPlanDto();
			subscriptionPlanDto.setPlanName(plan.getPlanName());
			shopDto.setSubscriptionPlan(subscriptionPlanDto);
		}

		return shopDto;
	}

}