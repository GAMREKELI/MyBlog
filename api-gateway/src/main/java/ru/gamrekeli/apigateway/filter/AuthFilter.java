package ru.gamrekeli.apigateway.filter;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class AuthFilter implements GatewayFilter, Ordered {


    @Autowired
    private RestTemplate restTemplate;
    private static final String AUTH_HEADER = "Authorization";
    private static final String AUTH_SERVICE_URL = "http://8070:authentication-service/validate-token";



    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        String token = request.getHeaders().getFirst(AUTH_HEADER);

        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);

            // Вызов auth-service для проверки токена
            ResponseEntity<String> authResponse = restTemplate.exchange(
                    AUTH_SERVICE_URL + "?token=" + token,
                    HttpMethod.GET,
                    null,
                    String.class
            );

            if (authResponse.getStatusCode() == HttpStatus.OK) {
                return chain.filter(exchange);
            } else {
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                return response.setComplete();
            }
        } else {
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
