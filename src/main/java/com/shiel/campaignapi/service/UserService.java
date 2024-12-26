package com.shiel.campaignapi.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.shiel.campaignapi.dto.SignupUserDto;
import com.shiel.campaignapi.entity.PasswordResetToken;
import com.shiel.campaignapi.entity.User;
import com.shiel.campaignapi.exception.UserNotFoundException;
import com.shiel.campaignapi.repository.PasswordResetTokenRepository;
import com.shiel.campaignapi.repository.UserRepository;

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
				signupDto.setAge(user.getAge());
				signupDto.setEmail(user.getEmail());
				signupDto.setGender(user.getGender());
				signupDto.setPlace(user.getPlace());
				signupDto.setRoles(user.getRoles());
				signupDto.setUserId(user.getUserId());
				signupDto.setStatus(user.getStatus());
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
				user.setAge(userDto.getAge());
				user.setGender(userDto.getGender());
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

	public SignupUserDto findUserById(Integer userId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new UserNotFoundException("No user found with ID: " + userId));

		return mapUserToDto(user);
	}

	private SignupUserDto mapUserToDto(User user) {
		SignupUserDto dto = new SignupUserDto();
		dto.setUserId(user.getUserId());
		dto.setFullName(user.getFullName());
		dto.setEmail(user.getEmail());
		dto.setPhone(user.getPhone());
		dto.setAge(user.getAge());
		dto.setGender(user.getGender());
		dto.setPlace(user.getPlace());
		dto.setRoles(user.getRoles());
		return dto;
	}

	public PasswordResetToken createPasswordResetToken(User user) {
		String otp = generateOtp();
		LocalDateTime expiryDate = LocalDateTime.now().plusMinutes(10); // Token valid for 10 min
		PasswordResetToken resetToken = new PasswordResetToken(otp, user, expiryDate);
		passwordResetTokenRepository.save(resetToken);
		return resetToken;
	}

	public String generateOtp() {
		Random random = new Random();
		int otp = 100000 + random.nextInt(900000); // Generates a number between 100000 and 999999
		return String.valueOf(otp);
	}

	public void sendUpdateEmail(String recipientEmail, String userName, String updateType) {
		String subject = "Account Update Notification";
		String message = String.format("Dear %s,\n\n" + "Your account information has been successfully updated.\n"
				+ "Update Type: %s\n" + "Date and Time: %s\n\n"
				+ "If you did not request this update, please contact our support team immediately.\n\n"
				+ "Best Regards,\n" + "Your Company Team", userName, updateType, LocalDateTime.now());

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
