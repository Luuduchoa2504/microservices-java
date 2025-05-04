package com.example.identity_service.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtil {
    // Hardcoded secret key (Base64 encoded to ensure sufficient length for HS512)
    private final String secret = "dGhpc2lzYXZlcnlsb25nc2VjcmV0a2V5dGhhdGlzYXQbGVhc3QzMmJ5dGVz"; // Replace with your own
    private final Key key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(secret));
    private final long expirationTime = 1000 * 60 * 60; // 1 hour

    public JwtUtil() {
        // Log the secret key on startup for debugging (remove in production)
        System.out.println("Identity Service JwtUtil initialized with secret: " + secret);
    }

    public String generateToken(String username, String role) {
        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(key)
                .compact();
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}