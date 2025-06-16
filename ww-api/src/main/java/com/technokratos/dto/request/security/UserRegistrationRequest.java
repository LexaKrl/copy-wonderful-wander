package com.technokratos.dto.request.security;

import com.technokratos.validation.password.ValidPassword;
import com.technokratos.validation.username.ValidUsername;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

@Schema(description = "DTO для регестрации пользователя")
public record UserRegistrationRequest(
        @Schema(description = "Username пользователя", example = "ivanich_777", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "Username is required")
        @Length(min = 3, max = 64, message = "Username length should be between 3 to 64 characters")
        @ValidUsername(message = "Username should contain only a-z, 0-9 and _")
        String username,

        @Schema(description = "Пароль пользователя", example = "Ivan111#", requiredMode = Schema.RequiredMode.REQUIRED)
        @ValidPassword(message = "The password must contain at least 8 characters, at least 1 capital letter, and at least 1 digit. The password must not contain spaces.")
        String password,

        @Schema(description = "Дублирование пароля пользователя", example = "Ivan111#", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "Duplicate password is required")
        String duplicatePassword,

        @Schema(description = "Электронная почта пользователя", example = "user@example.com", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "Email is required")
        @Email(message = "Email should be valid")
        String email,

        @Schema(description = "Имя пользователя", example = "Иван", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "Firstname is required")
        @Length(min = 2, max = 64, message = "Firstname length should be between 2 to 64 characters")
        String firstname,

        @Schema(description = "Фамилия пользователя", example = "Иванов", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "Lastname is required")
        @Length(min = 2, max = 64, message = "Lastname length should be between 2 to 64 characters")
        String lastname) {
}
