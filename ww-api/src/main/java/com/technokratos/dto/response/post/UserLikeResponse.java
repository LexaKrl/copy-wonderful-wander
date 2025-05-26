package com.technokratos.dto.response.post;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

public record UserLikeResponse(
        @Schema(description = "Уникальный идентификатор пользователя UUID", example = "550e8400-e29b-41d4-a716-446655440000")
        UUID userId,
        @Schema(description = "Username пользователя", example = "ivanich_777")
        String username,
        @Schema(description = "Ссылка на аватар пользователя", example = "https://example.com/avatar.jpg ")
        String avatarUrl
) {
}
