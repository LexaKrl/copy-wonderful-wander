package com.technokratos.dto.response.comment;

import java.util.List;

public record CommentHierarchyResponse(
        RootCommentResponse rootComment,
        List<ReplyCommentResponse> replies
) {
}
