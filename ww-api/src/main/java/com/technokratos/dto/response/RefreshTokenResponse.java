package com.technokratos.dto.response;

public record RefreshTokenResponse(
        String refreshToken,
        String accessToken) {
}
