package com.technokratos.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.technokratos.enums.security.UserRole;
import com.technokratos.exception.InvalidJwtException;
import com.technokratos.exception.UnauthorizedException;
import com.technokratos.service.JwtService;
import com.technokratos.util.HttpHeaders;
import com.technokratos.util.mapper.UserMapper;
import com.technokratos.validator.RouteValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationGatewayFilterFactory extends AbstractGatewayFilterFactory<Object> {

    private final JwtService jwtService;
    private final RouteValidator routeValidator;
    private final AntPathMatcher pathMatcher = new AntPathMatcher();
    private final UserMapper userMapper;

    public static final String BEARER_PREFIX = "Bearer ";
    public static final String AUTHORIZATION_HEADER_NAME = "Authorization";

    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();

            String authHeader = null;
            String token = null;
            UUID userId = null;
            String username = null;
            UserRole userRole = null;

            if (routeValidator.isSecured(request)) {
                authHeader = request.getHeaders().getOrDefault(AUTHORIZATION_HEADER_NAME, List.of()).getFirst();
                if (authHeader == null || authHeader.isEmpty()) {
                    try {
                        return onError(exchange, HttpStatus.UNAUTHORIZED, new UnauthorizedException("The authentication header is missing"));
                    } catch (Exception e) {
                        log.error("Error while processing unauthorized request", e);
                        throw new RuntimeException(e);
                    }
                }

                if (!authHeader.startsWith(BEARER_PREFIX)) {
                    try {
                        return onError(exchange, HttpStatus.UNAUTHORIZED, new InvalidJwtException("Invalid access token"));
                    } catch (Exception e) {
                        log.error("Error while processing unauthorized request", e);
                        throw new RuntimeException(e);
                    }
                }

                token = authHeader.substring(BEARER_PREFIX.length());

                try {
                    userId = jwtService.extractUserId(token);
                    username = jwtService.extractUsername(token);
                    userRole = userMapper.toUserRole(jwtService.extractUserRole(token));
                } catch (InvalidJwtException e) {
                    try {
                        return onError(exchange, HttpStatus.UNAUTHORIZED, e);
                    } catch (IOException ex) {
                        log.error("Error while processing unauthorized request", ex);
                        throw new RuntimeException(ex);
                    }
                }


                if (jwtService.isTokenExpired(token)) {
                    try {
                        return onError(exchange, HttpStatus.UNAUTHORIZED, new InvalidJwtException("Access token was expired"));
                    } catch (Exception e) {
                        log.error("Error while processing unauthorized request", e);
                        throw new RuntimeException(e);
                    }
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

    private static Mono<Void> onError(ServerWebExchange webExchange,
                                      HttpStatus status,
                                      RuntimeException exception) throws IOException {

        ServerHttpResponse response = webExchange.getResponse();
        response.setStatusCode(status);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("timestamp", LocalDateTime.now().toString());
        errorResponse.put("error", exception.getClass().getSimpleName());
        errorResponse.put("message", exception.getMessage());

        try {
            byte[] responseBody = new ObjectMapper().writeValueAsBytes(errorResponse);
            DataBuffer buffer = response.bufferFactory().wrap(responseBody);
            return response.writeWith(Mono.just(buffer));
        } catch (JsonProcessingException e) {
            return Mono.error(e);
        }

    }
}