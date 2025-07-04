package com.technokratos.dto.response.post;

import com.technokratos.dto.response.user.UserCompactResponse;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.UUID;

@Schema(description = "DTO представляющий все данные поста")
public record PostResponse(
        @Schema(description = "Уникальный идентификатор поста UUID", example = "550e8400-e29b-41d4-a716-446655440000")
        String postId,
        @Schema(description = "Подпись к фотографии", example = "Это мы в Дубае))",
                requiredMode = Schema.RequiredMode.REQUIRED)
        String title,
        @Schema(description = "Ссылка на фото поста, по которой можно получить фото из облачного хранилища",
                example = "https://example.com/photo.jpg")
        String imageUrl,
        @Schema(description = "Основные данные категории")
        CategoryResponse category,
        @Schema(description = "Основные данные пользователя")
        UserCompactResponse user,
        @Schema(description = "Общее количество лайков у поста", example = "124")
        long likesCount,
        @Schema(description = "Общее количество комментариев у поста", example = "52")
        long commentsCount,
        @Schema(description = "Время создания поста", example = "2023-10-05 14:30:00")
        LocalDateTime createdAt
) {
}
