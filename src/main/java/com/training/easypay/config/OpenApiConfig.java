package com.training.easypay.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        final String securitySchemeName = "BearerAuth";

        return new OpenAPI()
                // 1. Basic API Information
                .info(new Info()
                        .title("EasyRoll API")
                        .version("v1.0")
                        .description("API documentation for the EasyRoll payroll application.")
                )
                // 2. External Documentation
                .externalDocs(new ExternalDocumentation()
                        .description("Project GitHub Repository")
                        .url("https://github.com/your-repo/easyroll-backend") // Replace with your actual repo URL
                )
                // 3. Security Scheme Definition
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName, new SecurityScheme()
                                .name(securitySchemeName)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .description("Enter JWT Bearer token to authorize.")
                        )
                )
                // 4. Apply Security Globally
                .security(List.of(new SecurityRequirement().addList(securitySchemeName)));
    }
}
