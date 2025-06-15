package com.technokratos.dto.response.user;

import com.technokratos.enums.security.UserRole;
import com.technokratos.enums.user.PhotoVisibility;
import com.technokratos.enums.user.WalkVisibility;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(description = "DTO представляющий все данные пользователя")
public record UserResponse(
        @Schema(description = "Уникальный идентификатор пользователя UUID", example = "550e8400-e29b-41d4-a716-446655440000")
        UUID userId,
        @Schema(description = "Имя пользователя (логин)", example = "ivan_ivanov", maxLength = 64)
        String username,
        @Schema(description = "Электронная почта пользователя", example = "user@example.com")
        String email,
        @Schema(description = "Имя пользователя", example = "Иван")
        String firstname,
        @Schema(description = "Фамилия пользователя", example = "Иванов")
        String lastname,
        @Schema(description = "Описание профиля пользователя", example = "Люблю гулять и читать книги", maxLength = 254)
        String bio,
        @Schema(description = "Роль пользователя в системе", example = "ROLE_USER")
        UserRole role,
        @Schema(description = "Ссылка на аватар пользователя", example = "https://example.com/avatar.jpg ")
        String avatarUrl,
        @Schema(description = "Количество подписчиков пользователя", example = "150")
        int followersCount,
        @Schema(description = "Количество пользователей, на которых подписан пользователь", example = "200")
        int followingCount,
        @Schema(description = "Количество друзей пользователя", example = "100")
        int friendsCount,
        @Schema(description = "Уровень видимости своих фотографий другим пользователям", example = "FRIENDS_ONLY")
        PhotoVisibility myPhotoVisibility,
        @Schema(description = "Уровень видимости сохраненных фотографий другим пользователям", example = "FRIENDS_ONLY")
        PhotoVisibility savedPhotoVisibility,
        @Schema(description = "Уровень видимости своих прогулок другим пользователям", example = "PUBLIC")
        WalkVisibility walkVisibility) {
}
