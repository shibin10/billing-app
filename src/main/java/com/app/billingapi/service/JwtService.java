package com.app.billingapi.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.app.billingapi.entity.Role;
import com.app.billingapi.entity.User;
import com.app.billingapi.repository.RoleRepository;

@Service
public class JwtService {

	@Autowired
	RoleRepository roleRepository;

	@Value("${security.jwt.secret-key}")
	private String secretKey;

	@Value("${security.jwt.expiration-time}")
	private long jwtExpiration;

	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	public String generateToken(UserDetails userDetails, User user) {
		return generateToken(new HashMap<>(), user, userDetails);
	}

	public String generateToken(Map<String, Object> extraClaims, User user, UserDetails userDetails) {

		String roleName = "ROLE_USER";

		if (!user.getRoles().isEmpty()) {
			Long roleId = user.getRoles().get(0).getRoleId(); // get first roleId
			roleName = roleRepository.findById(roleId).map(Role::getRoleName).orElse("ROLE_USER");

			extraClaims.put("roleId", user.getRoles().get(0).getRoleId());
			extraClaims.put("roleName", roleName);
		}
		extraClaims.put("userId", user.getUserId());
		extraClaims.put("fullName", user.getFullName());

		return buildToken(extraClaims, userDetails, jwtExpiration);
	}

	public long getExpirationTime() {
		return jwtExpiration;
	}

	private String buildToken(Map<String, Object> extraClaims, UserDetails userDetails, long expiration) {

		return Jwts.builder()
				.setClaims(extraClaims)
				.setSubject(userDetails
						.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + expiration))
				.signWith(getSignInKey(), SignatureAlgorithm.HS256).compact();
	}

	public boolean isTokenValid(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
	}

	public Long extractUserId(String token) {
		Claims claims = extractAllClaims(token);
		return claims.get("userId", Long.class);
	}

	public String extractFullName(String token) {
		Claims claims = extractAllClaims(token);
		return claims.get("fullName", String.class);
	}

	public String extractRoleId(String token) {
		Claims claims = extractAllClaims(token);
		return claims.get("roleId", String.class); // Extract roleId
	}

	public String extractRoleName(String token) {
		Claims claims = extractAllClaims(token);
		return claims.get("roleName", String.class); // Extract roleName from token
	}

	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	private Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	public Claims extractAllClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(token).getBody();
	}

	private Key getSignInKey() {
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		return Keys.hmacShaKeyFor(keyBytes);
	}
}
