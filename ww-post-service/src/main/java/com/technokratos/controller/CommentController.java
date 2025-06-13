package com.technokratos.controller;

import com.technokratos.api.CommentApi;
import com.technokratos.dto.request.post.CommentRequest;
import com.technokratos.dto.response.post.CommentHierarchyResponse;
import com.technokratos.dto.response.post.RootCommentResponse;
import com.technokratos.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;


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
        return null;
    }

    @Override
    public CommentHierarchyResponse createComment(String postId, CommentRequest commentRequest) {
        return null;
    }

    @Override
    public CommentHierarchyResponse updateComment(String postId, String commentId, CommentRequest commentRequest) {
        return null;
    }

    @Override
    public void deleteComment(String postId, String commentId) {
    }

    private String getCurrentUserId() {
        return "00000000-0000-0000-0000-000000000001";
    }
}
