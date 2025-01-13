package com.apigateway.config;

import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

@Configuration
public class CustomFilters {

    @Bean
    public GlobalFilter customPreFilter() {
        return (exchange, chain) -> {
            // Örnek: Loglama, Header manipülasyonu vb.
            System.out.println("Global Pre Filter");
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                // Post Filter
                System.out.println("Global Post Filter");
            }));
        };
    }
}

