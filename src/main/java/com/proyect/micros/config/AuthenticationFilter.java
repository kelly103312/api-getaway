package com.proyect.micros.config;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    public AuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            if (!isAuthValid(exchange)) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }
            return chain.filter(exchange);
        };
    }

    private boolean isAuthValid(ServerWebExchange exchange) {
        HttpHeaders headers = exchange.getRequest().getHeaders();
        String authToken = headers.getFirst(HttpHeaders.AUTHORIZATION);

        // Aquí puedes validar el token con el servicio de autenticación.
        return authToken != null && validateTokenWithAuthService(authToken);
    }

    private boolean validateTokenWithAuthService(String authToken) {
        // Lógica para validar el token llamando al servicio de autenticación
        return true; // Retorna true o false basado en la validación
    }

    public static class Config {
        // Configuraciones adicionales si son necesarias
    }
}
