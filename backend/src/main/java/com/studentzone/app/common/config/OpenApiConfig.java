package com.studentzone.app.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class OpenApiConfig {

    private static final String SECURITY_SCHEME_NAME = "bearerAuth";

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Study Zone API")
                        .description("Backend REST API for Study Zone application. " +
                                "Use the **Authorize** button (🔒) to enter your JWT token.\n\n" +
                                "**Format:** `Bearer <your-token>` — or paste just the token, the prefix is optional.")
                        .version("v1.0.0")
                        .contact(new Contact()
                                .name("Study Zone Team")
                                .email("admin@studyzone.com")))
                // Register the Bearer security scheme globally
                .components(new Components()
                        .addSecuritySchemes(SECURITY_SCHEME_NAME, new SecurityScheme()
                                .name(SECURITY_SCHEME_NAME)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .description("Paste your JWT access token here (obtained from /api/auth/login).")))
                // Apply security scheme to all endpoints by default
                .addSecurityItem(new SecurityRequirement().addList(SECURITY_SCHEME_NAME));
    }
}
