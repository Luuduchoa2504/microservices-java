package com.example.product_service.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Base64;
import java.util.List;

@Component
public class JwtUtil {
    private final String secret = "dGhpc2lzYXZlcnlsb25nc2VjcmV0a2V5dGhhdGlzYXQbGVhc3QzMmJ5dGVz";
    private final byte[] secretBytes = Base64.getDecoder().decode(secret);

    public JwtUtil() {
        System.out.println("Product Service JwtUtil initialized with secret: " + secret);
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretBytes).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parser().setSigningKey(secretBytes).parseClaimsJws(token).getBody().getSubject();
    }

    public List<String> getRolesFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(secretBytes).parseClaimsJws(token).getBody();
        String role = claims.get("role", String.class);
        if (role != null) {
            return Arrays.asList("ROLE_" + role); // Prepend ROLE_ to match Spring Security convention
        }
        return claims.get("roles", List.class);
    }
}