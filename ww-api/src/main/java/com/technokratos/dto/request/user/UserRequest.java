package com.technokratos.dto.request.user;

import com.technokratos.enums.user.PhotoVisibility;
import com.technokratos.enums.user.WalkVisibility;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

@Schema(description = "DTO для обновления данных пользователя")
public record UserRequest(
        @Schema(description = "Электронная почта пользователя", example = "user@example.com", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "Email is required")
        @Email(message = "Email should be valid")
        String email,
        @Schema(description = "Имя пользователя", example = "Иван", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "First name is required")
        @Length(min = 3, max = 64, message = "Firstname length should be between 3 to 64 characters")
        String firstname,
        @Schema(description = "Фамилия пользователя", example = "Иванов", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "Lastname is required")
        @Length(min = 3, max = 64, message = "Lastname length should be between 3 to 64 characters")
        String lastname,
        @Schema(description = "Описание в профиле пользователя", example = "Люблю гулять и читать книги", maxLength = 254)
        @Length(max = 254, message = "Bio cannot exceed 254 characters")
        String bio,
        @Schema(description = "Уровень видимости своих фотографий другим пользователям", example = "FRIENDS_ONLY", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "Photo visibility is required")
        PhotoVisibility myPhotoVisibility,
        @Schema(description = "Уровень видимости сохраненных фотографий другим пользователям", example = "FRIENDS_ONLY", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "Photo visibility is required")
        PhotoVisibility savedPhotoVisibility,
        @Schema(description = "Уровень видимости своих прогулок другим пользователям", example = "PUBLIC", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "Walk visibility is required")
        WalkVisibility walkVisibility) {
}