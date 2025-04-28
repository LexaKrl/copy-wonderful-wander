package com.technokratos.service.auth;

import com.technokratos.dto.request.UserForJwtTokenRequest;
import com.technokratos.dto.response.UserLoginResponse;
import com.technokratos.model.UserEntity;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.function.Function;

import io.jsonwebtoken.Claims;

@Service
public class JWTService {

    private final String secretKey;

    public JWTService() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256");
            SecretKey sk = keyGenerator.generateKey();
            secretKey = Base64.getEncoder().encodeToString(sk.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public UserLoginResponse generateToken(UserForJwtTokenRequest userInfo) {

        Map<String, Object> claims = new HashMap<>();
        claims.put("id", userInfo.id());

        String accessToken = Jwts.builder()
                                    .claims()
                                    .add(claims)
                                    .subject(userInfo.username())
                                    .issuedAt(new Date(System.currentTimeMillis()))
                                    .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                                    .and()
                                    .signWith(getKey())
                                    .compact();

        return UserLoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken("")
                .id(userInfo.id())
                .build();
    }

    private SecretKey getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUserName(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

}
