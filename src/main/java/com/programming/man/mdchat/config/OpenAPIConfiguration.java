package com.programming.man.mdchat.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfiguration {


    @Bean
    public OpenAPI expenseAPI() {
        return new OpenAPI()
                .info(new Info().title("MDChat API")
                        .description("API for MDChat Application")
                        .version("v0.0.1")
                        .license(new License().name("Apache License Version 2.0").url("google.com")));
    }
}
