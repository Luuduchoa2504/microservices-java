package com.example.product_service.api;

import com.example.product_service.client.IdentityClient;
import com.example.product_service.dto.ProductDTO;
import com.example.product_service.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/products")
@Slf4j
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private IdentityClient identityClient;

    private void authenticateWithToken(String token, String requiredRole) {
        log.info("Setting security context with token: {}, role: {}", token, requiredRole);
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                "admin", null, Collections.singletonList(new SimpleGrantedAuthority(requiredRole))
        );
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<ProductDTO>> getAllProducts(HttpServletRequest request) {
        try {
            String token = identityClient.getToken("admin", "admin123");
            if (token == null) {
                log.warn("Failed to retrieve token for getAllProducts");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            authenticateWithToken(token, "ROLE_USER");
            return ResponseEntity.ok(productService.getAllProducts());
        } catch (Exception e) {
            log.error("Error in getAllProducts: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable Long id, HttpServletRequest request) {
        try {
            String token = identityClient.getToken("admin", "admin123");
            if (token == null) {
                log.warn("Failed to retrieve token for getProduct");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            authenticateWithToken(token, "ROLE_USER");
            return ResponseEntity.ok(productService.getProduct(id));
        } catch (Exception e) {
            log.error("Error in getProduct: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO productDTO, HttpServletRequest request) {
        try {
            String token = identityClient.getToken("admin", "admin123");
            if (token == null) {
                log.warn("Failed to retrieve token for createProduct");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            authenticateWithToken(token, "ROLE_ADMIN");
            return ResponseEntity.ok(productService.createProduct(productDTO));
        } catch (Exception e) {
            log.error("Error in createProduct: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO, HttpServletRequest request) {
        try {
            String token = identityClient.getToken("admin", "admin123");
            if (token == null) {
                log.warn("Failed to retrieve token for updateProduct");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            authenticateWithToken(token, "ROLE_ADMIN");
            return ResponseEntity.ok(productService.updateProduct(id, productDTO));
        } catch (Exception e) {
            log.error("Error in updateProduct: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id, HttpServletRequest request) {
        try {
            String token = identityClient.getToken("admin", "admin123");
            if (token == null) {
                log.warn("Failed to retrieve token for deleteProduct");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            authenticateWithToken(token, "ROLE_ADMIN");
            productService.deleteProduct(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("Error in deleteProduct: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}