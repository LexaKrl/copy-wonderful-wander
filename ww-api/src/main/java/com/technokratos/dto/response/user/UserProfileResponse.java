package com.technokratos.dto.response.user;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(description = "DTO представляющий все публичные данные пользователя")
public record UserProfileResponse(
        @Schema(description = "Уникальный идентификатор пользователя UUID", example = "550e8400-e29b-41d4-a716-446655440000")
        UUID userId,
        @Schema(description = "Имя пользователя (логин)", example = "ivan_ivanov", maxLength = 64)
        String username,
        @Schema(description = "Имя пользователя", example = "Иван")
        String firstname,
        @Schema(description = "Фамилия пользователя", example = "Иванов")
        String lastname,
        @Schema(description = "Описание профиля пользователя", example = "Люблю гулять и читать книги", maxLength = 254)
        String bio,
        @Schema(description = "Ссылка на аватар пользователя", example = "https://example.com/avatar.jpg")
        String avatarUrl,
        @Schema(description = "Количество подписчиков пользователя", example = "150")
        int followersCount,
        @Schema(description = "Количество пользователей, на которых подписан пользователь", example = "200")
        int followingCount,
        @Schema(description = "Количество друзей пользователя", example = "100")
        int friendsCount,
        @Schema(description = "Подписан ли текущий аутентифицированный пользователь на этого пользователя", example = "true")
        boolean isFollowedByUser,
        @Schema(description = "Подписан ли пользователь на текущего аутентифицированного пользователя")
        boolean isFollowingByUser,
        @Schema(description = "Является ли текущий аутентифицированный пользователь и этот пользователь друзьями", example = "false")
        boolean isFriends) {
}
