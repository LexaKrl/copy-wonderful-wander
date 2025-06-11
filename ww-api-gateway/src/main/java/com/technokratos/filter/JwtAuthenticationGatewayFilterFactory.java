package com.technokratos.filter;

import com.technokratos.service.JwtService;
import com.technokratos.util.HttpHeaders;
import com.technokratos.validator.RouteValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationGatewayFilterFactory extends AbstractGatewayFilterFactory<Object> {
    private final JwtService jwtService;
    private final RouteValidator routeValidator;
    public static final String BEARER_PREFIX = "Bearer ";


    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();

            if (routeValidator.isSecured.test(request)) {
                if (authMissing(request)) {
                    return onError(exchange, HttpStatus.UNAUTHORIZED);
                }

                String token = request.getHeaders().getOrEmpty("Authorization").getFirst()
                        .substring(BEARER_PREFIX.length());

                if (jwtService.isTokenExpired(token)) {
                    return onError(exchange, HttpStatus.UNAUTHORIZED);
                }

                ServerHttpRequest modifiedRequest = request.mutate()
                        .header(HttpHeaders.USER_ID, String.valueOf(jwtService.extractUserId(token)))
                        .header(HttpHeaders.USERNAME, jwtService.extractUsername(token))
                        .header(HttpHeaders.USER_ROLE, jwtService.extractUserRole(token).name())
                        .build();


                return chain.filter(exchange.mutate().request(modifiedRequest).build());
            }
            return chain.filter(exchange);
        };
    }

    private boolean authMissing(ServerHttpRequest request) {
        return !request.getHeaders().containsKey("Authorization");
    }

    private Mono<Void> onError(ServerWebExchange exchange, HttpStatus httpStatus) {
        exchange.getResponse().setStatusCode(httpStatus);
        return exchange.getResponse().setComplete();
    }
}