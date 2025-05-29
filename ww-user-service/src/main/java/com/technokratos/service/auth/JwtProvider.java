package com.technokratos.service.auth;

import com.technokratos.dto.request.security.UserForJwtTokenRequest;
import com.technokratos.dto.response.security.AuthResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class JwtProvider {

    private final RefreshTokenService refreshTokenService;
    private final JwtService jwtService;

    public AuthResponse generateTokens(UserForJwtTokenRequest userInfo) {
        log.info("Tokens generating...");
        log.info("userInfo: {}", userInfo);
        String accessToken = jwtService.generateAccessToken(userInfo);

        String refreshToken = refreshTokenService.createRefreshToken(userInfo.userId());

        return new AuthResponse(accessToken, refreshToken);
    }

}
