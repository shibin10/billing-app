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
import com.app.billingapi.entity.User;
import com.app.billingapi.exception.UserNotFoundException;
import com.app.billingapi.repository.PasswordResetTokenRepository;
import com.app.billingapi.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class UserService {
	private final UserRepository userRepository;
	private final PasswordResetTokenRepository passwordResetTokenRepository;
	private final PasswordEncoder passwordEncoder;
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	@Autowired
	private JavaMailSender mailSender;

	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder,
			PasswordResetTokenRepository passwordResetTokenRepository) {
		this.userRepository = userRepository;
		this.passwordResetTokenRepository = passwordResetTokenRepository;
		this.passwordEncoder = passwordEncoder;
	
	}

	public List<SignupUserDto> allUsers() {
		logger.info("Fetching all users");
		try {
			List<User> users = new ArrayList<>();
			userRepository.findAll().forEach(users::add);
			List<SignupUserDto> userDtoList = new ArrayList<>();

			for (User user : users) {
				SignupUserDto signupDto = new SignupUserDto();
				signupDto.setFullName(user.getFullName());			
				signupDto.setPhone(user.getPhone());
				signupDto.setEmail(user.getEmail());
				signupDto.setPlace(user.getPlace());
				signupDto.setRoles(user.getRoles());
				signupDto.setUserId(user.getUserId());
				signupDto.setStatus(user.getStatus());
				
				 User referredByUser = user.getReferredBy();
		            if (referredByUser != null) {
		                signupDto.setReferredByUserId(referredByUser.getUserId());
		            }
		            
				userDtoList.add(signupDto);
			}

			return userDtoList;
			
		} catch (Exception e) {
			
			logger.error("Error occurred while fetching all users", e);
			throw new RuntimeException("Failed to fetch users", e);
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

	private SignupUserDto mapUserToDto(User user) {
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
				+ "Best Regards,\n" + "Shiel Bible Home", fullName, updateType, LocalDateTime.now());

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

}
