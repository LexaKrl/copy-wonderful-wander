package com.technokratos.dto.response.post;

import java.util.List;

public record CommentHierarchyResponse(
        RootCommentResponse rootComment,
        List<ReplyCommentResponse> replies
) {
}
