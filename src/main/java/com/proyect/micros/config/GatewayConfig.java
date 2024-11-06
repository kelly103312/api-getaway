package com.proyect.micros.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;

@Configuration
public class GatewayConfig {

    private final AuthenticationFilter authenticationFilter;

    // Inyección del AuthenticationFilter
    public GatewayConfig(AuthenticationFilter authenticationFilter) {
        this.authenticationFilter = authenticationFilter;
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
            .route("servicio_kelly", r -> r.path("/kelly/**")
                .filters(f -> f.filter(authenticationFilter.apply(new AuthenticationFilter.Config()))
                               .stripPrefix(1))
                .uri("lb://SERVICIO-KELLY"))
            
            .route("servicio_lenin", r -> r.path("/lenin/**")
                .filters(f -> f.filter(authenticationFilter.apply(new AuthenticationFilter.Config()))
                               .stripPrefix(1))
                .uri("lb://SERVICIO-LENIN")) 
            
            .build();
    }
}
