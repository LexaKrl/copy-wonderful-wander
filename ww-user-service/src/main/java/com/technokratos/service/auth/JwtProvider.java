package com.technokratos.service.auth;

import com.technokratos.dto.UserInfoForJwt;
import com.technokratos.dto.response.security.AuthResponse;
import com.technokratos.service.JwtService;
import com.technokratos.util.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class JwtProvider {

    private final RefreshTokenService refreshTokenService;
    private final JwtService jwtService;
    private final UserMapper userMapper;

    public AuthResponse generateTokens(UserInfoForJwt userInfo) {
        log.info("Tokens generating...");
        log.info("userInfo: {}", userInfo);
        String accessToken = jwtService.generateAccessToken(userMapper.toStarterUserInfoJwt(userInfo));

        String refreshToken = refreshTokenService.createRefreshToken(userInfo.userId());

        return new AuthResponse(accessToken, refreshToken);
    }

}
