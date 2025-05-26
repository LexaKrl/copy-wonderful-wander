package com.technokratos.dto.response.post;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

public record PostResponse(
        @Schema(description = "Уникальный идентификатор поста UUID", example = "550e8400-e29b-41d4-a716-446655440000")
        UUID postId,
        @Schema(description = "Подпись к фотографии", example = "Это мы в Дубае))",
                requiredMode = Schema.RequiredMode.REQUIRED)
        String title,
        @Schema(description = "Ссылка на фото поста, по которой можно получить фото из облачного хранилища",
                example = "https://example.com/photo.jpg")
        String imageUrl,
        @Schema(description = "Уникальный идентификатор категории", example = "234523423")
        long categoryId,
        @Schema(description = "Username пользователя, которому принадлежит пост",
                example = "ivanich_777")
        String username,
        @Schema(description = "Ссылка на аватар пользователя, которому принадлежит пост",
                example = "https://example.com/avatar.jpg ")
        String avatarUrl,
        @Schema(description = "Общее количество лайков у поста", example = "124")
        long likesCount,
        @Schema(description = "Общее количество комментариев у поста", example = "52")
        long commentsCount
) {
}
