package com.technokratos.service;

import com.mongodb.client.result.DeleteResult;
import com.technokratos.dto.request.post.CommentRequest;
import com.technokratos.dto.response.comment.CommentHierarchyResponse;
import com.technokratos.dto.response.comment.CommentResponse;
import com.technokratos.dto.response.comment.ReplyCommentResponse;
import com.technokratos.dto.response.comment.RootCommentResponse;
import com.technokratos.dto.response.user.UserCompactResponse;
import com.technokratos.exception.*;
import com.technokratos.model.CommentEntity;
import com.technokratos.model.EmbeddedUser;
import com.technokratos.repository.CommentRepository;
import com.technokratos.repository.PostRepository;
import com.technokratos.repository.custom.CustomCommentRepository;
import com.technokratos.repository.custom.CustomPostRepository;
import com.technokratos.util.mapper.CommentMapper;
import com.technokratos.util.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
    private final PostRepository postRepository;
    private final UserMapper userMapper;
    private final CustomCommentRepository customCommentRepository;
    private final CustomPostRepository customPostRepository;

    public List<RootCommentResponse> getCommentsByPostId(String viewerId, String postId) {

        String userId = postService.getUserIdByPostId(postId);

        if (!postService.checkPostPrivacy(viewerId, userId, userService.getMyPhotoVisibility(userId))) {
            throw new ForbiddenServiceException("You are not authorized to view the comments of this post");
        }

        List<RootCommentResponse> comments = commentRepository.findByPostId(postId)
                .stream()
                .filter(commentEntity -> commentEntity.getRootCommentId() == null)
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

        CommentEntity rootComment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentByIdNotFoundException(commentId));

        if (!rootComment.getPostId().equals(postId)) {
            throw new NotFoundServiceException(
                    "Comment with id: %s will not be found in a post with id = %s".formatted(commentId, postId));
        }

        if (rootComment.getRootCommentId() != null) {
            throw new ConflictServiceException(
                    "No information was found on this comment, because it is not a root comment");
        }

        RootCommentResponse rootCommentResponse = commentMapper.toRootCommentResponse(
                rootComment,
                new UserCompactResponse(
                        UUID.fromString(rootComment.getUser().getUserId()),
                        rootComment.getUser().getUsername(),
                        rootComment.getUser().getUserId() + "test.jpg"));//todo сделать получение из минио

        List<ReplyCommentResponse> replyComments = commentRepository.findByRootCommentId(commentId)
                .stream()
                .map(comment -> commentMapper.toReplyCommentResponse(
                        comment,
                        new UserCompactResponse(
                                UUID.fromString(comment.getUser().getUserId()),
                                comment.getUser().getUsername(),
                                comment.getUser().getAvatarId() + "test.jpg") //todo сделать получение из минио
                ))
                .toList();

        return new CommentHierarchyResponse(rootCommentResponse, replyComments);
    }

    public CommentResponse createComment(String currentUserId, String postId, CommentRequest commentRequest) {
        if (!postRepository.existsById(postId)) {
            throw new PostByIdNotFoundException(postId);
        }

        EmbeddedUser user = userMapper.toEmbeddedUserEntity(userService.getUserById(currentUserId));

        CommentEntity comment = commentMapper.toCommentEntity(commentRequest);

        comment.setCommentId(String.valueOf(UUID.randomUUID()));
        comment.setPostId(postId);
        comment.setUser(user);
        comment.setRepliesCount(0L);
        comment.setCreatedAt(LocalDateTime.now());

        commentRepository.save(comment);

        customPostRepository.incrementCommentsCount(postId);

        log.info("createComment dto: {}", commentRequest);
        if (commentRequest.rootCommentId() != null) {
            log.info("createComment rootCommentId: {}", commentRequest.rootCommentId());
            customCommentRepository.incrementRepliesCount(commentRequest.rootCommentId());
        }

        return commentMapper.toCommentResponse(comment, postService.getCommentCountByPostId(postId));
    }

    public CommentResponse updateComment(String currentUserId, String postId, String commentId, CommentRequest commentRequest) {

        if (!postRepository.existsById(postId)) {
            throw new PostByIdNotFoundException(postId);
        }

        CommentEntity comment = commentRepository.findById(commentId).orElseThrow(() -> new CommentByIdNotFoundException(postId));

        if (!comment.getUser().getUserId().equals(currentUserId)) {
            throw new ForbiddenServiceException("You don`t have authority to edit this comment");
        }

        comment.setText(commentRequest.text());

        commentRepository.save(comment);

        return commentMapper.toCommentResponse(comment, postService.getCommentCountByPostId(postId));
    }

    public void deleteComment(String currentUserId, String postId, String commentId) {
        commentRepository.findById(commentId)
                .ifPresent(comment -> {
                    if (!comment.getUser().getUserId().equals(currentUserId)) {
                        throw new ForbiddenServiceException("You don`t have authority to delete this post");
                    }
                    DeleteResult deleteResult = customCommentRepository.removeByCommentId(commentId);

                    if (deleteResult.getDeletedCount() > 0) {
                        customPostRepository.reduceCommentsCount(postId, deleteResult.getDeletedCount());
                        if (comment.getRootCommentId() != null) {
                            customCommentRepository.decrementRepliesCount(comment.getRootCommentId());
                        }
                    }
                });
    }

}
