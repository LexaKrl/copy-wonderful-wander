package com.technokratos.dto.request.security;

import jakarta.validation.constraints.NotBlank;

public record RefreshTokenRequest(
        @NotBlank(message = "The refresh token cannot be empty")
        String refreshToken) {
}
