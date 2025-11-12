package com.app.billingapi.util;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

@Component
public class JwtUtils {

    private final String SECRET_KEY = "3cfa76ef14937c1c0ea519f8fc057a80fcd04a7420f8e8bcd0a7567c272e007b";

    public Claims extractAllClaims(String token) {
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

    	return Jwts.parser()
                   .setSigningKey(SECRET_KEY)
                   .parseClaimsJws(token)
                   .getBody();
    }

    public Long extractShopId(String token) {
        Claims claims = extractAllClaims(token);
        return claims.get("shopId", Long.class);
    }

    public Long extractUserId(String token) {
        Claims claims = extractAllClaims(token);
        return claims.get("userId", Long.class);
    }
    
    public String extractRoleName(String token) {
    	  Claims claims = extractAllClaims(token);
    	  return claims.get("roleName", String.class);
    }
    public String extractUsername(String token) {
  	  Claims claims = extractAllClaims(token);
  	  return claims.get("email", String.class);

  }
}
