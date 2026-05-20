package com.app.quantitymeasurement.config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenProvider {

    @Value("${app.jwt.secret}")
    private String jwtSecret;

    @Value("${app.jwt.expiration}")
    private long jwtExpirationInMs;

    private Key key;

    @PostConstruct
    public void init() {
        // Properties se secret key lekar cryptographic signing key taiyar karna
        this.key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    // Successful Google Login ke baad JWT Token generate karne ka method
    public String generateToken(Authentication authentication) {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        
        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");
        String picture = oAuth2User.getAttribute("picture");

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

        // Custom Claims (Payload) mein user ki details daalna
        Map<String, Object> claims = new HashMap<>();
        claims.put("name", name);
        claims.put("picture", picture);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // JWT Token se user ka Email (Subject) nikalne ka method
    public String getEmailFromJWT(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    // Incoming Request ke JWT Token ko validate karne ka method
    public boolean validateToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(authToken);
            return true;
        } catch (JwtException | IllegalArgumentException ex) {
            // Token invalid, expired ya tampered hone par false return karega
            return false;
        }
    }
}