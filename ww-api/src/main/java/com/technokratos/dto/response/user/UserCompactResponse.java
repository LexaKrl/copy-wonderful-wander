package com.technokratos.dto.response.user;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(description = "DTO представляющий краткую информацию о пользователе")
public record UserCompactResponse(
        @Schema(description = "Уникальный идентификатор пользователя UUID", example = "550e8400-e29b-41d4-a716-446655440000")
        UUID userId,
        @Schema(description = "Имя пользователя (логин)", example = "ivan_ivanov", maxLength = 64)
        String username,
        @Schema(description = "Ссылка на аватар пользователя", example = "https://example.com/avatar.jpg")
        String avatarUrl) {
}
