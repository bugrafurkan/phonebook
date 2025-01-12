package com.example.directoryservice.config;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springdoc.core.models.GroupedOpenApi;

@Configuration
public class SwaggerConfig {
    @Bean
    public SwaggerConfig swaggerConfig() {
        return new SwaggerConfig();
    }

    @Bean
    public GroupedOpenApi directoryServiceOpenApi(){
        return GroupedOpenApi.builder().
                group("directory-service").
                addOpenApiCustomizer(openApi -> {
                    openApi.setInfo(new Info()
                    .title("Directory Service API")
                            .description("This is a Directory Service API")
                            .version("v1.0")
                            .license(new License().name("Apache 2.0")));
                }).
                build();
    }
}
