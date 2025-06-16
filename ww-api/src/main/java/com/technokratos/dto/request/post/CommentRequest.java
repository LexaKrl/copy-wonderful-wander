package com.technokratos.dto.request.post;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

import java.util.UUID;

@Schema(description = "DTO для создания и обновления комментария")
public record CommentRequest(
        @Schema(description = "Текст комментария", example = "Вау, крутая фотка!", requiredMode = Schema.RequiredMode.REQUIRED)
        @Length(min = 1, max = 512, message = "Comment text length should be between 1 to 512 characters")
        @NotBlank(message = "Comment text is required")
        String text,
        @Schema(description = "ID комментария на который ответили. Если 'null' - комментарий не является ответом"
                , example = "550e8400-e29b-41d4-a716-446655440000")
        @Max(value = 36, message = "Root comment uuid length should be less 36 characters")
        String rootCommentId,
        @Schema(description = "Username пользователя которому ответили в ответах. Если 'null' - комментарий не является ответом"
                , example = "550e8400-e29b-41d4-a716-446655440000")
        @Length(min = 3, max = 64, message = "Parent comment username length should be between 3 to 64 characters")
        String parentCommentUsername
) {
}
