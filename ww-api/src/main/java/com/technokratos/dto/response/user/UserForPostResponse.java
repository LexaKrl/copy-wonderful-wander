package com.technokratos.dto.response.user;

import com.technokratos.enums.user.PhotoVisibility;
import com.technokratos.enums.user.WalkVisibility;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.util.UUID;

@Schema(description = "DTO для отправки данных пользователя в микросервис post-service")
public record UserForPostResponse(
        @Schema(description = "Уникальный идентификатор пользователя UUID", example = "550e8400-e29b-41d4-a716-446655440000")
        UUID userId,
        @Schema(description = "Имя пользователя (логин)", example = "ivan_ivanov", maxLength = 64)
        String username,
        @Schema(description = "Название аватара пользователя", example = "550e8400-e29b-41d4-a716-446655440000.jpg")
        String avatarFilename,
        @Schema(description = "Уровень видимости своих фотографий другим пользователям", example = "FRIENDS_ONLY", requiredMode = Schema.RequiredMode.REQUIRED)
        PhotoVisibility myPhotoVisibility,
        @Schema(description = "Уровень видимости сохраненных фотографий другим пользователям", example = "FRIENDS_ONLY", requiredMode = Schema.RequiredMode.REQUIRED)
        PhotoVisibility savedPhotoVisibility
) {
}
