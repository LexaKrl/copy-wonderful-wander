package com.technokratos.dto.response.comment;

import com.technokratos.dto.response.user.UserCompactResponse;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "DTO представляющий все данные комментария, который является ответом на корневой комментарий")
public record ReplyCommentResponse(
        @Schema(description = "Уникальный идентификатор комментария UUID", example = "550e8400-e29b-41d4-a716-446655440000")
        String commentId,
        @Schema(description = "Текст комментария", example = "@ivanich_777 согласен, крутая фотка!")
        String text,
        @Schema(description = "Основные данные пользователя")
        UserCompactResponse user,
        @Schema(description = "Username пользователя, которому ответили данным комментарие", example = "ivanich_777")
        String parentCommentUsername,
        @Schema(description = "Дата добавления комментария", example = "2014-04-08 12:30")
        LocalDateTime createdAt
) {
}
