package com.technokratos.dto.request.post;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

public record CommentRequest(
        @Schema(description = "Текст комментария", example = "Вау, крутая фотка!", requiredMode = Schema.RequiredMode.REQUIRED)
        String text,
        @Schema(description = "ID комментария на который ответили. Если 'null' - комментарий не является ответом"
                , example = "550e8400-e29b-41d4-a716-446655440000")
        String rootCommentId,
        @Schema(description = "Username пользователя которому ответили в ответах. Если 'null' - комментарий не является ответом"
                , example = "550e8400-e29b-41d4-a716-446655440000")
        String parentCommentUsername
) {
}
