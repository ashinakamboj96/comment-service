package com.intuit.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI usersMicroserviceOpenAPI() {
        OpenAPI info;
        info = new OpenAPI()
                .info(new Info().title("Social Media Comments Service")
                        .description("Exposing APIs to create a social media comments service")
                        .version("1.0"));
        return info;
    }
}
