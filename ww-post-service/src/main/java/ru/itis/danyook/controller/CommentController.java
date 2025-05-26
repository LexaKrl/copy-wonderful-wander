package ru.itis.danyook.controller;

import com.technokratos.api.CommentApi;
import com.technokratos.dto.request.post.CommentRequest;
import com.technokratos.dto.response.post.RootCommentResponse;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;


@RestController
public class CommentController implements CommentApi {
    @Override
    public List<RootCommentResponse> getCommentsByPostId(UUID postId) {
        return List.of();
    }

    @Override
    public List<RootCommentResponse> getCommentById(UUID postId, UUID commentId) {
        return List.of();
    }

    @Override
    public List<RootCommentResponse> createComment(UUID postId, CommentRequest commentRequest) {
        return List.of();
    }

    @Override
    public List<RootCommentResponse> updateComment(UUID postId, UUID commentId, CommentRequest commentRequest) {
        return List.of();
    }

    @Override
    public void deleteComment(UUID postId, UUID commentId) {

    }
}
