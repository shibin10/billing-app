package com.app.billingapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.Collections;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
	private final JwtAuthenticationFilter jwtAuthenticationFilter;

	public SecurityConfiguration(JwtAuthenticationFilter jwtAuthenticationFilter) {
		this.jwtAuthenticationFilter = jwtAuthenticationFilter;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable())
			.authorizeHttpRequests(auth -> auth
				.requestMatchers(
					"/auth/*",
					"/events/all",
					"/events/{eventId}",
					"/users/forgot-password",
					"/users/reset-password",
					"/country/all",
					"/otp/generate"
				).permitAll()

				// ADMIN or OWNER roles
				.requestMatchers(
					"/shop/add", "/shop/{shopId}", "/shop/user/{userId}", "/shop/update/*",
					"/shop/delete/*", "/shop/place/", "/shop/all", "/booking/all", "/users/all",
					"/booking/event/{eventId}", "/booking/delete/{bookingId}", "/roles/*",
					"/country/add", "/plan/*", "/plan/update/*", "/plan/delete/*"
				).hasAnyRole("ADMIN", "OWNER")

				// USER or ADMIN roles
				.requestMatchers(
					"/users/update/*", "/users/{userId}", "/users/delete/*", "/customer/add",
					"/customer/all", "/customer/update/{customerId}", "/customer/delete/*",
					"/customer/{customerId}", "/products/add", "/products/all",
					"/products/delete/{productId}", "/products/{productId}", 
					"/products/update/*", "/plan/{planId}", "/sales/*", 
					"/invoice/*", "/invoice-items/*","/sales-items/add"
				).hasAnyRole("USER", "ADMIN")

				// Anything else requires authentication
				.anyRequest().authenticated()
			)

			.anonymous(anonymous -> anonymous
				.authorities(Collections.singletonList(new SimpleGrantedAuthority("ROLE_APPLICATION")))
			)
			.sessionManagement(session -> session
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			)
			.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}


	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();

		configuration.setAllowedOrigins(List.of("http://localhost:8005"));
		configuration.setAllowedMethods(List.of("GET", "POST"));
		configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

		source.registerCorsConfiguration("/**", configuration);

		return source;
	}
}
