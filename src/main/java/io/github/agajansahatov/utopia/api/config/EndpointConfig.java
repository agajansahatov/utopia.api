package io.github.agajansahatov.utopia.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class EndpointConfig {
    @Bean
    public List<String> nonProtectedEndpoints() {
        return List.of(
                "/api/auth",
                "/api/public",
                "/api/products/*"
        );
    }
}
