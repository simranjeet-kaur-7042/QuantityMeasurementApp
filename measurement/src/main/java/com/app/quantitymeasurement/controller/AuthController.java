package com.app.quantitymeasurement.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @GetMapping("/login")
    public void redirectToGoogle(HttpServletResponse response) throws IOException {
        response.sendRedirect("/oauth2/authorization/google");
    }

    @GetMapping("/profile")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Map<String, Object>> getUserProfile(Authentication authentication) {
        Map<String, Object> profileData = new HashMap<>();
        
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            
            // Chunki Filter standard UserDetails de raha hai jisme username hi email hai
            profileData.put("email", userDetails.getUsername());
            profileData.put("name", userDetails.getUsername().split("@")[0]); // Email se naam nikal rahe hain temporary (e.g., simranjeet)
            profileData.put("status", "Authenticated");
            profileData.put("message", "Welcome to Quantity Measurement App!");
            
            return ResponseEntity.ok(profileData);
        }
        
        profileData.put("status", "Unauthorized");
        return ResponseEntity.status(401).body(profileData);
    }
}