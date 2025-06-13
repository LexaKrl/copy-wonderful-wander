package com.technokratos.service;

import com.technokratos.dto.response.post.RootCommentResponse;
import com.technokratos.exception.ForbiddenServiceException;
import com.technokratos.model.CommentEntity;
import com.technokratos.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostService postService;
    private final UserService userService;

    public List<RootCommentResponse> getCommentsByPostId(String viewerId, String postId) {

        String userId = postService.getUserIdByPostId(postId);

        if (!postService.checkPostPrivacy(viewerId, userId, userService.getMyPhotoVisibility(userId))) {
            throw new ForbiddenServiceException("You are not authorized to view the comments of this post");
        }

        List<CommentEntity> commentEntities = commentRepository.findByPostId(postId);

        return null;
    }
}
