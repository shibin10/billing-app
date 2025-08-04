package com.app.billingapi.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;

@Component
public class AuthUtil {
@Autowired
    private  JwtUtils jwtUtils;
    private final HttpServletRequest request;

    public AuthUtil(JwtUtils jwtUtils, HttpServletRequest request) {
        this.jwtUtils = jwtUtils;
        this.request = request;
    }

    public Long getLoggedInShopId() {
        String token = extractTokenFromHeader();
        return jwtUtils.extractShopId(token);
    }

    private String extractTokenFromHeader() {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        throw new RuntimeException("Missing or invalid Authorization header");
    }
}
