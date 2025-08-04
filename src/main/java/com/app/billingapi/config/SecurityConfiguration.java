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
		http.cors().and().csrf(csrf -> csrf.disable())
				.authorizeHttpRequests(auth -> auth
						.requestMatchers("/auth/*", "/users/forgot-password", "/users/reset-password", "/country/all",
								"/otp/generate")
						.permitAll()

						// ADMIN or OWNER roles
						.requestMatchers("/sales/*", "/sales-items/*", "/customer/*", "/reports/*", "/invoice/*",
								"/products/*", "/shop/{shopId}", "/shop/update/*", "/users/add", "/users/shop/staff",
								"/users/shop/getstaff", "/users/delete/*")
						.hasAnyRole("OWNER", "ADMIN")

						// USER or ADMIN roles
						.requestMatchers("/roles/*", "/plan/*", "/users/update/*", "/users/{userId}", "/users/delete/*",
								"/shop/add", "/shop/delete/*", "/shop/all")
						.hasAnyRole("ADMIN")

						.anyRequest().authenticated())
				.anonymous(anonymous -> anonymous
						.authorities(Collections.singletonList(new SimpleGrantedAuthority("ROLE_APPLICATION"))))
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowedOriginPatterns(List.of("*"));
		config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
		config.setAllowedHeaders(List.of("*"));
		config.setAllowCredentials(false);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);
		return source;

	}
}
