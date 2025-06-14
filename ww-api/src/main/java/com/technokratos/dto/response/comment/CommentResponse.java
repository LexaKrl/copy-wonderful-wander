package com.technokratos.dto.response.comment;

import com.technokratos.dto.response.user.UserCompactResponse;

import java.time.LocalDateTime;

public record CommentResponse(
        String commentId,
        String text,
        UserCompactResponse user,
        LocalDateTime createdAt,
        String parentCommentUsername,
        String rootCommentId,
        String postCommentsCount,
        String repliesCount
) {
}
