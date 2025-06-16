package com.technokratos.dto.request.security;

import com.technokratos.validation.password.ValidPassword;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

@Schema(description = "DTO для авторизации пользователя")
public record UserLoginRequest(
        @Schema(description = "Username пользователя", example = "ivanich_777", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "Username is required")
        @Length(min = 3, max = 64, message = "Username length should be between 3 to 64 characters")
        String username,

        @Schema(description = "Пароль пользователя", example = "Ivan111#", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "Password is required")
        String password) {
}
