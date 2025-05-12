package com.technokratos.service.auth;

import com.technokratos.dto.response.security.AuthResponse;
import com.technokratos.exception.UserByIdNotFoundException;
import com.technokratos.repository.RefreshTokenRepository;
import com.technokratos.repository.UserRepository;
import com.technokratos.tables.pojos.Account;
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
    private final JWTService jwtService;
    private final UserRepository userRepository;//todo переделать на сервис
    private final UserMapper userMapper;
    private final DateConverter dateConverter;


    public String createRefreshToken(UUID userId) {
        Account user = userRepository.findById(userId)
                .orElseThrow(() -> new UserByIdNotFoundException(userId));
        String token = jwtService.generateRefreshToken(user.getUsername());
        LocalDateTime expiryDate = dateConverter.dateToLocalDateTime(jwtService.extractExpiration(token));

        RefreshToken refreshToken = new RefreshToken(UUID.randomUUID(), token, userId, expiryDate);

        refreshTokenRepository.save(refreshToken);
        return token;
    }

    public AuthResponse refreshAccessToken(String refreshToken) {
        RefreshToken storedToken = refreshTokenRepository.findByToken(refreshToken)
                .orElseThrow(() -> new RuntimeException("Invalid Refresh Token"));

        UUID userId = storedToken.getUserId();
        String newRefreshToken = storedToken.getToken();

        Instant expiryInstant = dateConverter.localDateTimeToInstant(storedToken.getExpiryDate());
        if (expiryInstant.isBefore(Instant.now())) {
            refreshTokenRepository.deleteByToken(storedToken.getToken());
            throw new RuntimeException("Refresh Token was expired");
        }

        if (jwtService.isTokenExpiredSoon(refreshToken)) {
            refreshTokenRepository.deleteByToken(storedToken.getToken());
            newRefreshToken = createRefreshToken(userId);
            log.info("The refresh token has been updated to: {}", newRefreshToken);
        }

        Account account = userRepository.findById(userId).orElseThrow(() -> new UserByIdNotFoundException(userId));
        String newAccessToken = jwtService.generateAccessToken(userMapper.toJwtUserInfo(account));

        return new AuthResponse(newAccessToken, newRefreshToken);
    }

    public void deleteRefreshToken(String token) {
        refreshTokenRepository.deleteByToken(token);
    }
}