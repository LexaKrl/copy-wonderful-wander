package com.technokratos.service;

import com.mongodb.client.result.DeleteResult;
import com.technokratos.dto.response.PageResponse;
import com.technokratos.dto.response.comment.RootCommentResponse;
import com.technokratos.dto.response.post.LikeResponse;
import com.technokratos.dto.response.user.UserCompactResponse;
import com.technokratos.exception.ConflictServiceException;
import com.technokratos.exception.ForbiddenServiceException;
import com.technokratos.exception.PostByIdNotFoundException;
import com.technokratos.model.CommentEntity;
import com.technokratos.model.EmbeddedUser;
import com.technokratos.model.LikeEntity;
import com.technokratos.repository.LikeRepository;
import com.technokratos.repository.custom.CustomLikeRepository;
import com.technokratos.repository.PostRepository;
import com.technokratos.repository.custom.CustomPostRepository;
import com.technokratos.util.Pagination;
import com.technokratos.util.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final UserMapper userMapper;
    private final UserService userService;
    private final PostRepository postRepository;
    private final PostService postService;
    private final CustomLikeRepository customLikeRepository;
    private final CustomPostRepository customPostRepository;
    private final MinioService minioService;


    public PageResponse<UserCompactResponse> getLikesByPostId(String viewerId, String postId, int page, int size) {

        Pageable pageable = PageRequest.of(page, size);


        String userId = postService.getUserIdByPostId(postId);

        if (!postService.checkPostPrivacy(viewerId, userId, userService.getMyPhotoVisibility(userId))) {
            throw new ForbiddenServiceException("You are not authorized to view the likes of this post");
        }

        Page<LikeEntity> likesPage = likeRepository.findByPostId(postId, pageable);

        int total = likesPage.getTotalPages();
        int offset = Pagination.offset(total, page, size);

        List<UserCompactResponse> likes = likesPage
                .stream()
                .map(LikeEntity::getUser)
                .map(embeddedUser -> new UserCompactResponse(
                        UUID.fromString(embeddedUser.getUserId()),
                        embeddedUser.getUsername(),
                        minioService.getPresignedUrl(embeddedUser.getAvatarFilename())
                ))
                .toList();

        return PageResponse.<UserCompactResponse>builder()
                .data(likes)
                .total(total)
                .limit(size)
                .offset(offset)
                .build();

    }

    public LikeResponse createLike(String currentUserId, String postId) {

        if (!postRepository.existsById(postId)) {
            throw new PostByIdNotFoundException(postId);
        }

        if (customLikeRepository.likeExists(currentUserId, postId)) {
            throw new ConflictServiceException("The like has already been set");
        }

        EmbeddedUser user = userMapper.toEmbeddedUserEntity(userService.getUserById(currentUserId));

        LikeEntity like = LikeEntity.builder()
                .likeId(String.valueOf(UUID.randomUUID()))
                .postId(postId)
                .user(user)
                .build();

        likeRepository.save(like);

        customPostRepository.incrementLikesCount(postId);

        return new LikeResponse(postService.getLikesCountByPostId(postId));
    }

    public LikeResponse deleteLike(String currentUserId, String postId) {

        DeleteResult deleteResult = customLikeRepository.removeByUserIdAndPostId(currentUserId, postId);

        if (deleteResult.getDeletedCount() > 0) {
            customPostRepository.decrementLikesCount(postId);
        }

        return new LikeResponse(postService.getLikesCountByPostId(postId));
    }

}
