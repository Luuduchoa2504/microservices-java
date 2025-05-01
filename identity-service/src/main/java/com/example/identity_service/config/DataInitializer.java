package com.example.identity_service.config;

import com.example.identity_service.dto.UserDTO;
import com.example.identity_service.service.IdentityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {
    @Autowired
    @Lazy
    private IdentityService identityService;

    @Override
    public void run(String... args) throws Exception {
        // Initialize default admin user
        UserDTO adminUser = new UserDTO();
        adminUser.setUsername("admin");
        adminUser.setPassword("admin123");
        adminUser.setRoles(Set.of("USER", "ADMIN"));

        try {
            identityService.register(adminUser);
            System.out.println("Default admin user created: username=admin, password=admin123");
        } catch (IllegalArgumentException e) {
            System.out.println("Default admin user already exists: " + e.getMessage());
        }
    }
}
