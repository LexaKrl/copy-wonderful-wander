package com.technokratos.dto.request.security;

import com.technokratos.validation.password.ValidPassword;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "DTO для смены пароля пользователя")
public record PasswordChangeRequest(

        @Schema(description = "Старый пароль пользователя", example = "ivanIvan11", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "Password is required")
        String oldPassword,

        @Schema(description = "Новый пароль пользователя", example = "ivanIvan22", requiredMode = Schema.RequiredMode.REQUIRED)
        @ValidPassword(message = "The password must contain at least 8 characters, at least 1 capital letter, and at least 1 digit. The password must not contain spaces.")
        String newPassword,

        @Schema(description = "Дублирование нового пароля пользователя", example = "ivanIvan22", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "Duplicate password is required")
        String newDuplicatePassword) {
}

