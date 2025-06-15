package com.technokratos.filter;

import com.technokratos.config.SpringSecurityConfig;
import com.technokratos.enums.security.UserRole;
import com.technokratos.exception.InvalidJwtException;
import com.technokratos.exception.UnauthorizedException;
import com.technokratos.model.UserPrincipal;
import com.technokratos.service.auth.JwtService;
import com.technokratos.util.ErrorResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.UnavailableException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    public static final String BEARER_PREFIX = "Bearer ";
    public static final String AUTHORIZATION_HEADER_NAME = "Authorization";

    private final JwtService jwtService;
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String path = request.getServletPath();
        for (String excludedUrl : SpringSecurityConfig.PUBLIC_URLS) {
            if (pathMatcher.match(excludedUrl, path)) {
                filterChain.doFilter(request, response);
                return;
            }
        }

        String authHeader = request.getHeader(AUTHORIZATION_HEADER_NAME);
        String token = null;
        UUID userId = null;
        String username = null;
        UserRole userRole = null;

        if (authHeader != null) {
            if (authHeader.startsWith(BEARER_PREFIX)) {
                token = authHeader.substring(BEARER_PREFIX.length());
                try {
                    userId = jwtService.extractUserId(token);
                    username = jwtService.extractUsername(token);
                    userRole = jwtService.extractUserRole(token);
                } catch (InvalidJwtException e) {
                    ErrorResponse.sendErrorResponse(response, HttpStatus.UNAUTHORIZED, e);
                    return;
                }
            } else {
                ErrorResponse.sendErrorResponse(response,
                        HttpStatus.UNAUTHORIZED,
                        new InvalidJwtException("Invalid access token"));
                return;
            }
        } else {
            ErrorResponse.sendErrorResponse(response,
                                            HttpStatus.UNAUTHORIZED,
                                            new UnauthorizedException("The authentication header is missing"));
            return;
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            if (jwtService.isTokenExpired(token)) {
                ErrorResponse.sendErrorResponse(response,
                                                HttpStatus.UNAUTHORIZED,
                                                new InvalidJwtException("Access token was expired")
                );
            }

            UserDetails userDetails = UserPrincipal.builder()
                                                   .userId(userId)
                                                   .username(username)
                                                   .role(userRole)
                                                   .password(null)
                                                   .build();

            if (jwtService.validateToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            } else {
                ErrorResponse.sendErrorResponse(response,
                                                HttpStatus.UNAUTHORIZED,
                                                new InvalidJwtException("The access token failed validation"));
            }
        }
        filterChain.doFilter(request, response);
    }
}
