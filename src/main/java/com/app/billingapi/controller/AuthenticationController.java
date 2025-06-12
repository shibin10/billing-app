package com.app.billingapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.billingapi.dto.SigninUserDto;
import com.app.billingapi.dto.SignupUserDto;
import com.app.billingapi.entity.User;
import com.app.billingapi.exception.UserBadRequest;
import com.app.billingapi.response.SigninResponse;
import com.app.billingapi.service.AuthenticationService;
import com.app.billingapi.service.JwtService;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {
	private final JwtService jwtService;
	private final AuthenticationService authenticationService;
	private final UserDetailsService userDetailsService;

	public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService,
			UserDetailsService userDetailsService) {
		this.jwtService = jwtService;
		this.authenticationService = authenticationService;
		this.userDetailsService = userDetailsService;
	}

	@PostMapping("/signup")
	public ResponseEntity<?> register(@RequestBody SignupUserDto signupUserDto) {


		if (!signupUserDto.getPhone().matches("^\\+?[1-9]\\d{8,11}$")) {
			  throw new UserBadRequest("Invalid Phone Number", "Invalid phone number", 400);
		}

		if (authenticationService.existsByPhone(signupUserDto.getPhone())) {
			  throw new UserBadRequest("Duplicate Phone Number", "Phone Number is already taken!", 400);
		}
		User registeredUser = authenticationService.signup(signupUserDto);

		return ResponseEntity.ok(registeredUser);
	}

	@PostMapping("/signin")
	public ResponseEntity<SigninResponse> authenticate(@RequestBody SigninUserDto signUserDto) {
		User authenticatedUser = authenticationService.authenticate(signUserDto);
		UserDetails userDetails = userDetailsService.loadUserByUsername(authenticatedUser.getEmail());
		String jwtToken = jwtService.generateToken(userDetails,authenticatedUser );

		SigninResponse loginResponse = new SigninResponse().setToken(jwtToken)
				.setExpiresIn(jwtService.getExpirationTime());

		return ResponseEntity.ok(loginResponse);
	}
}