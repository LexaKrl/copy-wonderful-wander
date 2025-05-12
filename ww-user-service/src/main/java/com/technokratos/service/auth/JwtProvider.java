package com.technokratos.service.auth;

import com.technokratos.dto.request.security.UserForJwtTokenRequest;
import com.technokratos.dto.response.security.AuthResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtProvider {

    private final RefreshTokenService refreshTokenService;
    private final JWTService jwtService;

    public AuthResponse generateTokens(UserForJwtTokenRequest userInfo) {
        String accessToken = jwtService.generateAccessToken(userInfo);

        String refreshToken = refreshTokenService.createRefreshToken(userInfo.userId());

        return new AuthResponse(accessToken, refreshToken);
    }

}
