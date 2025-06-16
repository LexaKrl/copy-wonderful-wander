package com.technokratos.dto.response.comment;

import com.technokratos.dto.response.user.UserCompactResponse;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "DTO представляющий все данные комментария при создание и обновление")
public record CommentResponse(
        @Schema(description = "Уникальный идентификатор комментария UUID", example = "550e8400-e29b-41d4-a716-446655440000")
        String commentId,
        @Schema(description = "Текст комментария", example = "@ivanich_777 согласен, крутая фотка!")
        String text,
        @Schema(description = "Основные данные пользователя")
        UserCompactResponse user,
        @Schema(description = "Дата добавления комментария", example = "2014-04-08 12:30")
        LocalDateTime createdAt,
        @Schema(description = "Username пользователя, которому ответили данным комментарие, если null, то коммент корневой",
                example = "ivanich_777")
        String parentCommentUsername,
        @Schema(description = "Уникальный идентификатор комментария UUID на который идет ответ, если null, то коммент корневой",
                example = "550e8400-e29b-41d4-a716-446655440000")
        String rootCommentId,
        @Schema(description = "Общее количество комментариев под постом, которому принадлежит данный комментарий", example = "23")
        String postCommentsCount,
        @Schema(description = "Общее количество ответов на комментарий", example = "23")
        String repliesCount
) {
}
