package com.ltp.contacts.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI springContactApi(){
        return new OpenAPI()
                .info(new Info()
                .title("Contact API")
                .description("An API that can manage contacts")
                .version("v1.0")
                );
    }
}
