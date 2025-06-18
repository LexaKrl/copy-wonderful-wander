package com.technokratos.dto.request.security;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "DTO для обновления jwt")
public record RefreshTokenRequest(
        @Schema(description = "Refresh token пользователя с помощью которого обновляется Access token",
                requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "The refresh token cannot be empty")
        String refreshToken) {
}
