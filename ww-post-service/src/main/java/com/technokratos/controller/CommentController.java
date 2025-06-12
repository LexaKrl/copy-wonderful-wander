package com.technokratos.controller;

import com.technokratos.api.CommentApi;
import com.technokratos.dto.request.post.CommentRequest;
import com.technokratos.dto.response.post.RootCommentResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;


@RestController
public class CommentController implements CommentApi {

    @Override
    public List<RootCommentResponse> getCommentsByPostId(String postId, Pageable pageable) {
        return List.of();
    }

    @Override
    public List<RootCommentResponse> getCommentById(String postId, String commentId) {
        return List.of();
    }

    @Override
    public List<RootCommentResponse> createComment(String postId, CommentRequest commentRequest) {
        return List.of();
    }

    @Override
    public List<RootCommentResponse> updateComment(String postId, String commentId, CommentRequest commentRequest) {
        return List.of();
    }

    @Override
    public void deleteComment(String postId, String commentId) {

    }
}
