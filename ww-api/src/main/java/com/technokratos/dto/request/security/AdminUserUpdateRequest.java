package com.technokratos.dto.request.security;

import com.technokratos.enums.security.UserRole;
import com.technokratos.validation.username.ValidUsername;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record AdminUserUpdateRequest(
        @Schema(description = "Username пользователя", example = "ivanich_777", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "Username name is required")
        @Length(min = 3, max = 64, message = "Username length should be between 3 to 64 characters")
        @ValidUsername(message = "Username should contain only a-z, 0-9 and _")
        String username,

        @Schema(description = "Электронная почта пользователя", example = "user@example.com", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "Email is required")
        @Email(message = "Email should be valid")
        String email,

        @Schema(description = "Роль пользователя", example = "ROLE_USER", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "User role is required")
        UserRole role) {
}
