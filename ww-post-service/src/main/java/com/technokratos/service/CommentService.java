package com.technokratos.service;

import com.technokratos.dto.response.post.CommentHierarchyResponse;
import com.technokratos.dto.response.post.PostResponse;
import com.technokratos.dto.response.post.RootCommentResponse;
import com.technokratos.dto.response.user.UserCompactResponse;
import com.technokratos.exception.ForbiddenServiceException;
import com.technokratos.model.CommentEntity;
import com.technokratos.repository.CommentRepository;
import com.technokratos.util.mapper.CommentMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostService postService;
    private final UserService userService;
    private final CommentMapper commentMapper;

    public List<RootCommentResponse> getCommentsByPostId(String viewerId, String postId) {

        String userId = postService.getUserIdByPostId(postId);

        if (!postService.checkPostPrivacy(viewerId, userId, userService.getMyPhotoVisibility(userId))) {
            throw new ForbiddenServiceException("You are not authorized to view the comments of this post");
        }

        List<RootCommentResponse> comments = commentRepository.findByPostId(postId)
                .stream()
                .map(comment -> commentMapper.toRootCommentResponse(
                        comment,
                        new UserCompactResponse(
                                UUID.fromString(comment.getUser().getUserId()),
                                comment.getUser().getUsername(),
                                comment.getUser().getAvatarId() + "test.jpg") //todo сделать получение из минио
                ))
                .toList();

        log.info("service getCommentsByPostId comments: {}", comments);

        return comments;//todo сделать правильную пагинацию
    }


    public CommentHierarchyResponse getCommentById(String viewerId, String postId, String commentId) {

        String userId = postService.getUserIdByPostId(postId);

        if (!postService.checkPostPrivacy(viewerId, userId, userService.getMyPhotoVisibility(userId))) {
            throw new ForbiddenServiceException("You are not authorized to view the comments of this post");
        }

        return null;
    }
}
