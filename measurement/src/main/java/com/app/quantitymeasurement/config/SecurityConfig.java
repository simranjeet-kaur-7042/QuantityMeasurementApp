package com.app.quantitymeasurement.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JwtTokenProvider tokenProvider;

    @Value("${app.security.public-paths}")
    private String[] publicPaths;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter, JwtTokenProvider tokenProvider) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.tokenProvider = tokenProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // 1. CORS Setup for Frontend Integration
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            
            // 2. CSRF Disable kyunki hum stateless JWT use kar rahe hain
            .csrf(csrf -> csrf.disable())
            
            // 3. H2 Console ko iFrame mein chalne dene ke liye frames disable/sameOrigin karna
            .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()))
            
            // 4. Session ko strictly Stateless banana (No cookies/session on server)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            
            // 5. Endpoint Permissions Configuration
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(publicPaths).permitAll() // application.properties wale paths allow honge
                .anyRequest().authenticated()            // Baki saari APIs lock ho jayengi
            )
            
            // 6. Google OAuth2 Login Setup
            .oauth2Login(oauth2 -> oauth2
                // Login successful hone par yeh callback execute hoga aur JWT return karega
                .successHandler((request, response, authentication) -> {
                    String token = tokenProvider.generateToken(authentication);
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    response.getWriter().write("{\"token\": \"" + token + "\", \"tokenType\": \"Bearer\"}");
                })
            );

        // 7. Hamare Custom JWT Filter ko Security Filter Chain mein add karna
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000", "http://localhost:4200")); // React/Angular ports
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
        configuration.setAllowCredentials(true);
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}