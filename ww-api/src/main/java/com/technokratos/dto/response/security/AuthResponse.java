package com.technokratos.dto.response.security;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


public record AuthResponse(
        @Schema(description = "Access token пользователя",
                requiredMode = Schema.RequiredMode.REQUIRED)
        String accessToken,
        @Schema(description = "Refresh token пользователя",
                requiredMode = Schema.RequiredMode.REQUIRED)
        String refreshToken) {
}
