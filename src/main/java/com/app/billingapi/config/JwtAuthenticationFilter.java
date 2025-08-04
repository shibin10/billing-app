package com.app.billingapi.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import com.app.billingapi.entity.Role;
import com.app.billingapi.entity.User;
import com.app.billingapi.repository.UserRepository;
import com.app.billingapi.service.JwtService;

import io.jsonwebtoken.Claims;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	private final HandlerExceptionResolver handlerExceptionResolver;

	private final JwtService jwtService;
	private final UserDetailsService userDetailsService;
	private final UserRepository userRepository;

	public JwtAuthenticationFilter(JwtService jwtService, UserDetailsService userDetailsService,
			HandlerExceptionResolver handlerExceptionResolver, UserRepository userRepository) {
		this.jwtService = jwtService;
		this.userDetailsService = userDetailsService;
		this.handlerExceptionResolver = handlerExceptionResolver;
		this.userRepository = userRepository;
	}

	@Override
	protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
			@NonNull FilterChain filterChain) throws ServletException, IOException {
		final String authHeader = request.getHeader("Authorization");

		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}

		try {
			final String jwt = authHeader.substring(7);
			final Claims claims = jwtService.extractAllClaims(jwt);
			
			final Long userId = claims.get("userId", Long.class);
			final Long shopId = claims.get("shopId", Long.class);
			final String fullName = claims.get("fullName", String.class);
			final String role = claims.get("roleName", String.class);
			final String email = jwtService.extractUsername(jwt);

			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

			if (email != null && authentication == null) {
				
				UserDetails userDetails = this.userDetailsService.loadUserByUsername(email);
				
				if (jwtService.isTokenValid(jwt, userDetails)) {

					 List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(role));

						UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
								null, authorities

						);
						  Map<String, Object> details = new HashMap<>();
				            details.put("userId",       userId);
				            details.put("fullName",     fullName);
				            details.put("roleName",     role);   
				            if (shopId != null) details.put("shopId", shopId);
				            details.put("requestDetails",
				                        new WebAuthenticationDetailsSource().buildDetails(request));

				            authToken.setDetails(details);

				            SecurityContextHolder.getContext().setAuthentication(authToken);
				        }
			}

			filterChain.doFilter(request, response);
		} catch (Exception exception) {
			handlerExceptionResolver.resolveException(request, response, null, exception);
		}
	}
}
