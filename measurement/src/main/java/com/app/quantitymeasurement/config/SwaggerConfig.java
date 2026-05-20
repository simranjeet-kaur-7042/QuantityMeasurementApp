package com.app.quantitymeasurement.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {

        return new OpenAPI()

                .info(new Info()

                        .title("Quantity Measurement API")

                        .version("1.0")

                        .description(
                                "Spring Boot REST API for Quantity Measurement Application"
                        ));
    }
}