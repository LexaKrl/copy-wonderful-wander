package com.technokratos.controller;

import com.technokratos.api.CommentApi;
import com.technokratos.dto.request.post.CommentRequest;
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
    public List<RootCommentResponse> getCommentsByPostId(String postId, Pageable pageable) {
        return commentService.getCommentsByPostId(getCurrentUserId(), postId);
    }

    @Override
    public CommentHierarchyResponse getCommentById(String postId, String commentId) {
        return commentService.getCommentById(getCurrentUserId(), postId, commentId);
    }

    @Override
    public CommentResponse createComment(String postId, CommentRequest commentRequest) {
        return commentService.createComment(getCurrentUserId(), postId, commentRequest);
    }

    @Override
    public CommentResponse updateComment(String postId, String commentId, CommentRequest commentRequest) {
        return commentService.updateComment(getCurrentUserId(), postId, commentId, commentRequest);
    }

    @Override
    public void deleteComment(String postId, String commentId) {
        commentService.deleteComment(getCurrentUserId(), postId, commentId);
    }

    private String getCurrentUserId() {
        return "00000000-0000-0000-0000-000000000001";
    }
}
