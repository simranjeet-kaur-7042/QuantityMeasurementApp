package com.app.quantitymeasurement.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    // 1. Redirect URL jo browser ko Google login screen par bhejega
    // Path: http://localhost:8080/api/auth/login
    @GetMapping("/login")
    public void redirectToGoogle(HttpServletResponse response) throws IOException {
        response.sendRedirect("/oauth2/authorization/google");
    }

    // 2. Secure Profile Endpoint - Sirf valid JWT token bearer hi ise call kar sakta hai
    // Path: http://localhost:8080/api/auth/profile
    @GetMapping("/profile")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Map<String, Object>> getUserProfile(@AuthenticationPrincipal UserDetails userDetails) {
        Map<String, Object> profileData = new HashMap<>();
        
        if (userDetails != null) {
            profileData.put("email", userDetails.getUsername());
            profileData.put("status", "Authenticated");
            profileData.put("message", "Welcome to Quantity Measurement App!");
            return ResponseEntity.ok(profileData);
        }
        
        profileData.put("status", "Unauthorized");
        return ResponseEntity.status(401).body(profileData);
    }
}