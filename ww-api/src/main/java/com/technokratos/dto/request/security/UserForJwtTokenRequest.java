package com.technokratos.dto.request.security;

import com.technokratos.enums.security.UserRole;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.util.UUID;

public record UserForJwtTokenRequest(
        @NotNull(message = "User id can not be null")
        UUID userId,

        @Schema(description = "Username пользователя", example = "Ivanich777", requiredMode = Schema.RequiredMode.REQUIRED)
        @Length(min = 3, max = 64, message = "Username length should be between 3 to 64 characters")
        @NotBlank(message = "Username can not be null")
        String username,

        @NotNull(message = "User role can not be null")
        UserRole role) {
}
