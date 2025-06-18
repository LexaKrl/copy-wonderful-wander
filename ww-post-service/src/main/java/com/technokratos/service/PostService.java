package com.technokratos.service;

import com.technokratos.dto.request.post.PostRequest;
import com.technokratos.dto.response.PageResponse;
import com.technokratos.dto.response.post.PostResponse;
import com.technokratos.dto.response.user.UserCompactResponse;
import com.technokratos.enums.user.PhotoVisibility;
import com.technokratos.exception.CategoryByIdNotFoundException;
import com.technokratos.exception.ConflictServiceException;
import com.technokratos.exception.ForbiddenServiceException;
import com.technokratos.exception.PostByIdNotFoundException;
import com.technokratos.model.*;
import com.technokratos.repository.*;
import com.technokratos.repository.custom.CustomPostRepository;
import com.technokratos.repository.custom.CustomSavedPostRepository;
import com.technokratos.util.Pagination;
import com.technokratos.util.mapper.PostMapper;
import com.technokratos.util.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {

    private final PostRepository postRepository;
    private final SavedPostRepository savedPostRepository;
    private final CategoryRepository categoryRepository;
    private final UserService userService;
    private final PostMapper postMapper;
    private final UserMapper userMapper;
    private final CustomPostRepository customPostRepository;
    private final CustomSavedPostRepository customSavedPostRepository;
    private final MinioService minioService;


    public PageResponse<PostResponse> getRecommendedPosts(String currentUserId, int page, int size) {
        return null;//todo после сервиса рекомендаций
    }

    public PageResponse<PostResponse> getSavedPostsByUserId(String viewerId, String userId, int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        if (!checkPostPrivacy(viewerId, userId, userService.getSavedPhotoVisibility(userId))) {
            throw new ForbiddenServiceException("You don`t have authority to see this post");
        }

        List<String> savedPostIds = customSavedPostRepository.getSavedPostIdsByUserId(userId);

        log.info("saved post ids: {}", savedPostIds);

        Page<PostEntity> postsPage = postRepository.findAllByPostIdIn(savedPostIds, pageable);

        int total = postsPage.getTotalPages();
        int offset = Pagination.offset(total, page, size);

        List<PostResponse> posts = postPageToPostResponse(postsPage);

        return PageResponse.<PostResponse>builder()
                .data(posts)
                .total(total)
                .limit(size)
                .offset(offset)
                .build();
    }

    public PageResponse<PostResponse> getPostsByUserId(String viewerId, String userId, int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        log.info("service getPostsByUserId data: viewerId: {}, userId: {}, myPhotoVisibility: {}",
                viewerId, userId, userService.getMyPhotoVisibility(userId));

        if (!checkPostPrivacy(viewerId, userId, userService.getMyPhotoVisibility(userId))) {
            throw new ForbiddenServiceException("You don`t have authority to see this post");
        }

        Page<PostEntity> postsPage = postRepository.findByUser_UserId(userId, pageable);

        int total = postsPage.getTotalPages();
        int offset = Pagination.offset(total, page, size);

        List<PostResponse> posts = postPageToPostResponse(postsPage);

        return PageResponse.<PostResponse>builder()
                .data(posts)
                .total(total)
                .limit(size)
                .offset(offset)
                .build();
    }

    public PostResponse getPostById(String viewerId, String postId) {

        PostEntity post = postRepository.findById(postId)
                .orElseThrow(() -> new PostByIdNotFoundException(postId));
        String userId = post.getUser().getUserId();

        if (!checkPostPrivacy(viewerId, userId, userService.getMyPhotoVisibility(userId))) {
            throw new ForbiddenServiceException("You don`t have authority to see this post");
        }

        return postMapper.toPostResponse(post, minioService.getPresignedUrl(post.getImageFilename()),
                new UserCompactResponse(
                        UUID.fromString(post.getUser().getUserId()),
                        post.getUser().getUsername(),
                        minioService.getPresignedUrl(post.getUser().getAvatarFilename())));
    }

    public PostResponse create(String currentUserId, PostRequest createPostRequest) {

        EmbeddedUser user = userMapper.toEmbeddedUserEntity(userService.getUserById(currentUserId));
        PostEntity post = postMapper.toPostEntity(createPostRequest);

        CategoryEntity category = categoryRepository.findById(createPostRequest.categoryId())
                .orElseThrow(() -> new CategoryByIdNotFoundException(createPostRequest.categoryId()));

        post.setPostId(String.valueOf(UUID.randomUUID()));
        post.setUser(user);
        post.setCreatedAt(LocalDateTime.now());
        post.setCategory(postMapper.toEmbeddedCategoryEntity(category));

        postRepository.save(post);

        return postMapper.toPostResponse(post, minioService.getPresignedUrl(post.getImageFilename()),
                new UserCompactResponse(
                        UUID.fromString(post.getUser().getUserId()),
                        post.getUser().getUsername(),
                        minioService.getPresignedUrl(post.getUser().getAvatarFilename())));
    }

    public PostResponse update(String currentUserId, String postId, PostRequest updatePostRequest) {
        PostEntity post = postRepository.findById(postId).orElseThrow(() -> new PostByIdNotFoundException(postId));

        if (!post.getUser().getUserId().equals(currentUserId)) {
            throw new ForbiddenServiceException("You don`t have authority to edit this post");
        }

        CategoryEntity category = categoryRepository.findById(updatePostRequest.categoryId())
                .orElseThrow(() -> new CategoryByIdNotFoundException(updatePostRequest.categoryId()));

        post.setTitle(updatePostRequest.title());
        post.setImageFilename(updatePostRequest.imageFilename());
        post.setCategory(postMapper.toEmbeddedCategoryEntity(category));

        postRepository.save(post);

        return postMapper.toPostResponse(post, minioService.getPresignedUrl(post.getImageFilename()),
                new UserCompactResponse(
                        UUID.fromString(post.getUser().getUserId()),
                        post.getUser().getUsername(),
                        minioService.getPresignedUrl(post.getUser().getAvatarFilename())));
    }

    public void delete(String currentUserId, String postId) {
        if (postRepository.existsById(postId)) {
            if (!getUserIdByPostId(postId).equals(currentUserId)) {
                throw new ForbiddenServiceException("You don`t have authority to delete this post");
            }
            postRepository.deleteById(postId);
            savedPostRepository.deleteByPostId(postId);
        }
    }

    public UUID savePost(String currentUserId, String postId) {

        checkSavedPost(currentUserId, postId);

        SavedPostEntity savedPost = new SavedPostEntity();

        savedPost.setSavedPostId(String.valueOf(UUID.randomUUID()));
        savedPost.setPostId(postId);
        savedPost.setUserId(currentUserId);
        savedPost.setSavedAt(LocalDateTime.now());

        log.info("saved post: {}", savedPost);

        savedPostRepository.save(savedPost);
        return UUID.fromString(postId);
    }

    public void deleteSavedPost(String currentUserId, String postId) {
        savedPostRepository.deleteByUserIdAndPostId(currentUserId, postId);
    }

    public Long getLikesCountByPostId(String postId) {
        PostEntity post = customPostRepository.getLikesCountByPostId(postId);

        if (post == null) {
            throw new PostByIdNotFoundException(postId);
        }
        return post.getLikesCount();
    }

    public Long getCommentCountByPostId(String postId) {
        PostEntity post = customPostRepository.getCommentsCountByPostId(postId);

        if (post == null) {
            throw new PostByIdNotFoundException(postId);
        }
        return post.getCommentsCount();
    }

    public boolean checkPostPrivacy(String viewerId, String userId, PhotoVisibility photoVisibility) {
        if (viewerId.equals(userId)) {
            return true;
        }

        boolean check = switch (photoVisibility) {
            case PhotoVisibility.PUBLIC -> true;
            case PhotoVisibility.PRIVATE -> false;
            case PhotoVisibility.FRIENDS_ONLY -> userService.isFriends(userId, viewerId);
        };
        log.info("checkPostPrivacy: {}, photoVisibility: {}", check, photoVisibility);
        log.info("view id: {}", viewerId);
        log.info("user id: {}", userId);
        return check;
    }

    private void checkSavedPost(String currentUserId, String postId) {

        String userId = getUserIdByPostId(postId);

        if (savedPostRepository.existsByUserIdAndPostId(currentUserId, postId)) {
            throw new ConflictServiceException("This post has already been saved");
        }

        if (!checkPostPrivacy(currentUserId, userId, userService.getMyPhotoVisibility(userId))) {
            throw new ForbiddenServiceException("You don`t have authority to save this post");
        }
    }

    public String getUserIdByPostId(String postId) {
        PostEntity post = customPostRepository.getUserIdByPostId(postId);

        if (post == null) {
            throw new PostByIdNotFoundException(postId);
        }

        return post.getUser().getUserId();
    }

    private List<PostResponse> postPageToPostResponse(Page<PostEntity> postsPage) {
        return postsPage
                .stream()
                .map(postEntity -> postMapper.toPostResponse(
                        postEntity,
                        minioService.getPresignedUrl(postEntity.getImageFilename()),
                        new UserCompactResponse(
                                UUID.fromString(postEntity.getUser().getUserId()),
                                postEntity.getUser().getUsername(),
                                minioService.getPresignedUrl(postEntity.getUser().getAvatarFilename()))
                ))
                .toList();
    }
}

