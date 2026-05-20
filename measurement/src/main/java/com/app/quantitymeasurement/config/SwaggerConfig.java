package com.app.quantitymeasurement.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        final String securitySchemeName = "Bearer Authentication";
        
        return new OpenAPI()
                .info(new Info()
                        .title("Quantity Measurement API")
                        .version("1.0")
                        .description("Spring Boot REST API for Quantity Measurement Application"))
                // 1. Pure Swagger UI par Global Security Requirement apply karna (Isse lock icons dikhenge)
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                // 2. Swagger UI ko batana ki authentication ke liye JWT Bearer use karna hai
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName,
                                new SecurityScheme()
                                        .name(securitySchemeName)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")));
    }
}