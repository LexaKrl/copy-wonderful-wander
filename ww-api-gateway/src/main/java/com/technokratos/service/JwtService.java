package com.technokratos.service;

import com.technokratos.config.properties.JwtProperties;
import com.technokratos.enums.UserRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.UUID;
import java.util.function.Function;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtService {
    private final JwtProperties jwtProperties;

    private SecretKey getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtProperties.getSecretKey());
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public UUID extractUserId(String token) {
        Claims claims = extractAllClaims(token);
        String userId = claims.get("userId", String.class);

        if (userId == null) {
            throw new RuntimeException("UserId in jwt is null");
        }

        return UUID.fromString(userId);
    }

    public UserRole extractUserRole(String token) {
        Claims claims = extractAllClaims(token);
        String userRole = claims.get("userRole", String.class);

        if (userRole == null) {
            throw new RuntimeException("Role in jwt is null");
        }

        return UserRole.valueOf(userRole);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(getKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (JwtException e) {
            log.warn("Invalid access token");
            return null;
//            throw new InvalidJwtException("Invalid access token");
        }
    }

//    public boolean validateToken(String token, UserDetails userDetails) {
//        final String username = extractUsername(token);
//        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
//    }

    public boolean isTokenExpiredSoon(String token) {
        Date expiration = extractExpiration(token);
        long timeLeft = expiration.getTime() - System.currentTimeMillis();
        return timeLeft < jwtProperties.getTimeTokenEndSoon();
    }

    public boolean isTokenExpired(String token) {
        Date expiration = extractExpiration(token);
        return expiration.before(new Date());
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
}
