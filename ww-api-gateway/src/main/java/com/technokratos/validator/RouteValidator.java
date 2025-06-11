package com.technokratos.validator;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
public class RouteValidator {
    public static final List<String> openEndpoints = List.of(
            "/api/auth/register",
            "/api/auth/login",
            "/api/auth/refresh-token",
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/swagger-ui.html",
            "/actuator/**",
            "/error"
    );

    public boolean isSecured(ServerHttpRequest serverHttpRequest) {
        return openEndpoints.stream()
                .noneMatch(uri -> serverHttpRequest.getURI().getPath().contains(uri));
    }
}
