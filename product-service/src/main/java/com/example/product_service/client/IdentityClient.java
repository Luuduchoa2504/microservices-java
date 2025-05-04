package com.example.product_service.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class IdentityClient {

    @Value("${identity.service.url:http://localhost:8082}")
    private String identityServiceUrl;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private String token;

    public IdentityClient(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    public String getToken(String username, String password) {
        if (token != null) {
            log.info("Reusing existing token: {}", token);
            return token;
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        Map<String, String> body = new HashMap<>();
        body.put("username", username);
        body.put("password", password);
        HttpEntity<Map<String, String>> request = new HttpEntity<>(body, headers);
        try {
            log.info("Requesting token from Identity Service at: {}", identityServiceUrl + "/identity/signin");
            ResponseEntity<String> response = restTemplate.exchange(
                    identityServiceUrl + "/identity/signin",
                    HttpMethod.POST,
                    request,
                    String.class
            );
            log.info("Received response from Identity Service: {}", response.getBody());
            JsonNode root = objectMapper.readTree(response.getBody());
            token = root.path("token").asText();
            if (token == null || token.isEmpty()) {
                log.error("Token is null or empty in response: {}", response.getBody());
                return null;
            }
            log.info("Successfully retrieved token: {}", token);
            return token;
        } catch (Exception e) {
            log.error("Failed to get token from Identity Service: {}", e.getMessage(), e);
            return null;
        }
    }
}