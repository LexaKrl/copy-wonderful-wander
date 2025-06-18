package com.technokratos.controller;

import com.technokratos.api.CommentApi;
import com.technokratos.dto.request.post.CommentRequest;
import com.technokratos.dto.response.PageResponse;
import com.technokratos.dto.response.comment.*;
import com.technokratos.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class CommentController implements CommentApi {

    private final CommentService commentService;

    @Override
    public PageResponse<RootCommentResponse> getCommentsByPostId(String currentUserId, String postId, Integer page, Integer size) {
        return commentService.getCommentsByPostId(currentUserId, postId, page, size);
    }

    @Override
    public CommentHierarchyResponse getCommentById(String currentUserId, String postId, String commentId) {
        return commentService.getCommentById(currentUserId, postId, commentId);
    }

    @Override
    public CommentResponse createComment(String currentUserId, String postId, CommentRequest commentRequest) {
        return commentService.createComment(currentUserId, postId, commentRequest);
    }

    @Override
    public CommentResponse updateComment(String currentUserId, String postId, String commentId, CommentRequest commentRequest) {
        return commentService.updateComment(currentUserId, postId, commentId, commentRequest);
    }

    @Override
    public void deleteComment(String currentUserId, String postId, String commentId) {
        commentService.deleteComment(currentUserId, postId, commentId);
    }
}
