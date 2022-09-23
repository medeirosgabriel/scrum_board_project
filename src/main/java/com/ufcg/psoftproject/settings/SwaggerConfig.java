package com.ufcg.psoftproject.settings;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;

@Configuration
public class SwaggerConfig {
	
	@Bean
    public OpenAPI customOpenAPI(){
        return new OpenAPI()
                .externalDocs(new ExternalDocumentation()
                        .description("PSOFT Project API Documentation"));
    }

}
