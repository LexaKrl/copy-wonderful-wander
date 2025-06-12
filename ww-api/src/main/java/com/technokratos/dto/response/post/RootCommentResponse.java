package com.technokratos.dto.response.post;

import com.technokratos.dto.response.user.UserCompactResponse;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.UUID;

public record RootCommentResponse(
        @Schema(description = "Уникальный идентификатор комментария UUID", example = "550e8400-e29b-41d4-a716-446655440000")
        String commentId,
        @Schema(description = "Текст комментария", example = "Вау, крутая фотка!")
        String text,
        @Schema(description = "Основные данные пользователя")
        UserCompactResponse user,
        @Schema(description = "Дата добавления комментария", example = "2014-04-08 12:30")
        LocalDateTime createdAt,
        @Schema(description = "Общее количество ответов на комментарий", example = "23")
        int repliesCount
) {
}
