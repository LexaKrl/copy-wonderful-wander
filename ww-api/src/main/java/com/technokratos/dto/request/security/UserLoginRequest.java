package com.technokratos.dto.request.security;

import com.technokratos.validation.password.ValidPassword;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

@Schema(description = "DTO для авторизации пользователя")
public record UserLoginRequest(
        @Schema(description = "Username пользователя", example = "ivanich_777", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "Username is required")
        @Length(min = 3, max = 64, message = "Username length should be between 3 to 64 characters")
        String username,

        @Schema(description = "Пароль пользователя", example = "Ivan111#", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "Password is required")
        String password,

        @Schema(description = "FCM токен для push-уведомлений",
                example = "dEfAuLtToKeN:APA91b...",
                requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        @Pattern(regexp = "^[A-Za-z0-9_\\-:]*$",
                message = "FCM token contains invalid characters. Only letters, numbers, '-', '_' and ':' are allowed")
        @Length(max = 200, message = "FCM token too long")
        String fcmToken) {
}
