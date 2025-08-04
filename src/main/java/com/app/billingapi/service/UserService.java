package com.app.billingapi.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.billingapi.dto.SignupUserDto;
import com.app.billingapi.entity.PasswordResetToken;
import com.app.billingapi.entity.Role;
import com.app.billingapi.entity.Shop;
import com.app.billingapi.entity.User;
import com.app.billingapi.enums.UserStatus;
import com.app.billingapi.exception.UserNotFoundException;
import com.app.billingapi.repository.PasswordResetTokenRepository;
import com.app.billingapi.repository.RoleRepository;
import com.app.billingapi.repository.ShopRepository;
import com.app.billingapi.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {
	private final UserRepository userRepository;
	private final PasswordResetTokenRepository passwordResetTokenRepository;
	private final PasswordEncoder passwordEncoder;
	private final ShopRepository shopRepository;
	private final RoleRepository roleRepository;
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	@Autowired
	private JavaMailSender mailSender;

	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder,
			PasswordResetTokenRepository passwordResetTokenRepository, ShopRepository shopRepository,
			RoleRepository roleRepository) {
		this.userRepository = userRepository;
		this.passwordResetTokenRepository = passwordResetTokenRepository;
		this.passwordEncoder = passwordEncoder;
		this.shopRepository = shopRepository;
		this.roleRepository = roleRepository;

	}

	
	
	public List<SignupUserDto> allUsers() {
		logger.info("Fetching all invoices");
		try {
			return userRepository.findAll().stream().map(this::mapUserToDto).collect(Collectors.toList());
		} catch (Exception e) {
			logger.error("Error fetching invoices", e);
			throw new RuntimeException("Failed to fetch Users", e);
		}
	}

	public User updateUser(SignupUserDto userDto) {
		logger.info("Updating user with ID: {}", userDto.getUserId());
		try {
			Optional<User> optionalUser = userRepository.findById(userDto.getUserId());

			if (optionalUser.isPresent()) {
				User user = optionalUser.get();

				user.setPlace(userDto.getPlace());
				user.setEmail(userDto.getEmail());
				user.setFullName(userDto.getFullName());
				user.setPhone(userDto.getPhone());
				

				if (userDto.getPassword() != null) {
					user.setPassword(passwordEncoder.encode(userDto.getPassword()));
				}

				if (userDto.getRoles() != null) {
					user.setRoles(userDto.getRoles());
				}

				return userRepository.save(user);
			} else {
				throw new RuntimeException("User not found with id " + userDto.getUserId());
			}
		} catch (Exception e) {
			logger.error("Error occurred while updating user with ID: {}", userDto.getUserId(), e);
			throw new RuntimeException("Error updating user" + e.getMessage(), e);
		}
	}

	public SignupUserDto findUserById(Long userId) {
		User user = userRepository.findById(userId).orElseThrow(
				() -> new UserNotFoundException("No user found with ID: " + userId, "Please register to login", 400));

		return mapUserToDto(user);
	}

	public SignupUserDto mapUserToDto(User user) {
		
		SignupUserDto dto = new SignupUserDto();
		
		dto.setUserId(user.getUserId());
		dto.setFullName(user.getFullName());
		dto.setEmail(user.getEmail());
		dto.setPhone(user.getPhone());
		dto.setPlace(user.getPlace());
		dto.setRoles(user.getRoles());
		
		return dto;
	}

	public PasswordResetToken createPasswordResetToken(User user) {
		String otp = generateOtp();
		LocalDateTime expiryDate = LocalDateTime.now().plusMinutes(10);
		PasswordResetToken resetToken = new PasswordResetToken(otp, user, expiryDate);
		passwordResetTokenRepository.save(resetToken);
		return resetToken;
	}

	public String generateOtp() {
		Random random = new Random();
		int otp = 100000 + random.nextInt(900000);
		return String.valueOf(otp);
	}

	public void sendUpdateEmail(String recipientEmail, String fullName, String updateType) {
		String subject = "Account Update Notification";
		String message = String.format("Dear %s,\n\n" + "Your account information has been successfully updated.\n"
				+ "Update Type: %s\n" + "Date and Time: %s\n\n"
				+ "If you did not request this update, please contact our support team immediately.\n\n"
				+ "Best Regards,\n" + "Billing App", fullName, updateType, LocalDateTime.now());

		SimpleMailMessage email = new SimpleMailMessage();
		email.setTo(recipientEmail);
		email.setSubject(subject);
		email.setText(message);

		mailSender.send(email);
	}

	public PasswordResetToken validatePasswordResetToken(String otp) {
		return passwordResetTokenRepository.findByToken(otp).filter(t -> t.getExpiryDate().isAfter(LocalDateTime.now()))
				.orElse(null);
	}

	public void invalidatePasswordResetToken(PasswordResetToken token) {
		passwordResetTokenRepository.delete(token);
	}

	public User createStaffForOwner(Long ownerId, SignupUserDto signupUserDto) {
	    logger.info("Creating staff for owner ID: {}", ownerId);

	    List<Shop> shops = shopRepository.findByOwner_UserId(ownerId);
	    if (shops.isEmpty()) {
	        throw new IllegalStateException("No shop found for this owner");
	    }

	    User staffUser = new User()
	            .setFullName(signupUserDto.getFullName())
	            .setEmail(signupUserDto.getEmail())
	            .setPassword(passwordEncoder.encode(signupUserDto.getPassword()))
	            .setPhone(signupUserDto.getPhone())
	            .setPlace(signupUserDto.getPlace())
	            .setStatus(UserStatus.ACTIVE);

	    Role staffRole;
	    if (signupUserDto.getRoleId() != null) {
	        staffRole = roleRepository.findById(signupUserDto.getRoleId())
	                .orElseThrow(() -> new IllegalArgumentException("Invalid role ID"));
	    } else {
	        staffRole = roleRepository.findByRoleName("ROLE_STAFF")
	                .orElseThrow(() -> new RuntimeException("Default STAFF role not found"));
	    }
	    staffUser.setRoles(List.of(staffRole));

	    staffUser.setShops(shops);  

	    return userRepository.save(staffUser);
	}

	public List<SignupUserDto> getStaffByShopOwner(Long ownerId) {
	  
		List<Shop> shops = shopRepository.findByOwner_UserId(ownerId);
	    if (shops.isEmpty()) {
	        throw new IllegalStateException("No shops found for this owner.");
	    }

	    Set<User> staffUsers = new HashSet<>();
	    
	    for (Shop shop : shops) {
	    	
	        List<User> users = userRepository.findByShops_ShopId(shop.getShopId());
	        
	        for (User user : users) {
	            if (!user.getUserId().equals(ownerId)) {
	                staffUsers.add(user); // exclude owner
	            }
	        }
	    }
	   

	    return staffUsers.stream()
	            .map(this::mapUserToDto)
	            .collect(Collectors.toList());
	}

}
