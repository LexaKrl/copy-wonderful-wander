package com.technokratos.service.auth;

import com.technokratos.dto.response.security.AuthResponse;
import com.technokratos.dto.response.user.UserResponse;
import com.technokratos.exception.InvalidJwtException;
import com.technokratos.repository.RefreshTokenRepository;
import com.technokratos.service.JwtService;
import com.technokratos.service.UserService;
import com.technokratos.tables.pojos.RefreshToken;
import com.technokratos.util.DateConverter;
import com.technokratos.util.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtService jwtService;
    private final UserService userService;
    private final UserMapper userMapper;
    private final DateConverter dateConverter;


    public String createRefreshToken(UUID userId) {
        log.info("Create refresh token for user with id = {}", userId);
        UserResponse user = userService.getUserById(userId);
        String token = jwtService.generateRefreshToken(user.username());
        LocalDateTime expiryDate = dateConverter.dateToLocalDateTime(jwtService.extractExpiration(token));

        RefreshToken refreshToken = new RefreshToken(UUID.randomUUID(), token, userId, expiryDate);

        refreshTokenRepository.save(refreshToken);
        return token;
    }

    public AuthResponse refreshAccessToken(String refreshToken) {
        RefreshToken storedToken = refreshTokenRepository.findByToken(refreshToken)
                .orElseThrow(() -> new InvalidJwtException("Invalid Refresh token"));

        UUID userId = storedToken.getUserId();
        String newRefreshToken = storedToken.getToken();

        if (jwtService.isTokenExpired(refreshToken)) {
            refreshTokenRepository.deleteByToken(storedToken.getToken());
            throw new InvalidJwtException("Refresh Token was expired");
        }

        if (jwtService.isTokenExpiredSoon(refreshToken)) {
            refreshTokenRepository.deleteByToken(storedToken.getToken());
            newRefreshToken = createRefreshToken(userId);
            log.info("The refresh token has been updated to: {}", newRefreshToken);
        }

        UserResponse user = userService.getUserById(userId);
        String newAccessToken = jwtService.generateAccessToken(userMapper.toStarterUserInfoJwt(user));

        return new AuthResponse(newAccessToken, newRefreshToken);
    }

    public void deleteRefreshToken(String token) {
        refreshTokenRepository.deleteByToken(token);
    }
}