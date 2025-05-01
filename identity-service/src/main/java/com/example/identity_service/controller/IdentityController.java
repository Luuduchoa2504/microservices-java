package com.example.identity_service.controller;

import com.example.identity_service.dto.LoginRequestDTO;
import com.example.identity_service.dto.LoginResponseDTO;
import com.example.identity_service.dto.UserDTO;
import com.example.identity_service.service.IdentityService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/identity")
public class IdentityController {

    @Autowired
    private IdentityService identityService;

    @PostMapping("/signup")
    public ResponseEntity<UserDTO> signup(@Valid @RequestBody UserDTO userDTO) {
        UserDTO createdUser = identityService.register(userDTO);
        return ResponseEntity.status(201).body(createdUser);
    }

    @PostMapping("/signin")
    public ResponseEntity<LoginResponseDTO> signin(@Valid @RequestBody LoginRequestDTO loginRequest) {
        LoginResponseDTO loginResponse = identityService.login(loginRequest);
        return ResponseEntity.ok(loginResponse);
    }
}
