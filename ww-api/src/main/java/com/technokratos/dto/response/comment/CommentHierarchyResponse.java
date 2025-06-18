package com.technokratos.dto.response.comment;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "DTO представляющий все данные о корневом коментарии и всех его ответов")
public record CommentHierarchyResponse(
        @Schema(description = "DTO представляющий все данные корневого комментария")
        RootCommentResponse rootComment,
        @Schema(description = "DTO представляющий все данные комментариев, которые являются ответами на корневой комментарий")
        List<ReplyCommentResponse> replies
) {
}
