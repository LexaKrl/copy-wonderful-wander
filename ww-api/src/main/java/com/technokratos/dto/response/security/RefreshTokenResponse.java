package com.technokratos.dto.response.security;

public record RefreshTokenResponse(
        String refreshToken,
        String accessToken) {
}
