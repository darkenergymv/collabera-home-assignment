package com.darkenergy.library_service.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
  info = @Info(
    title = "Library System API",
    version = "v1",
    description = "Simple library system with borrowers, books, and borrowing"
  )
)
public class SwaggerConfig {

}
