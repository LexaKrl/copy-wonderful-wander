package com.technokratos.service;

import com.technokratos.dto.request.post.PostRequest;
import com.technokratos.dto.response.post.PostResponse;
import com.technokratos.dto.response.user.UserCompactResponse;
import com.technokratos.enums.user.PhotoVisibility;
import com.technokratos.exception.CategoryByIdNotFoundException;
import com.technokratos.exception.ConflictServiceException;
import com.technokratos.exception.ForbiddenServiceException;
import com.technokratos.exception.PostByIdNotFoundException;
import com.technokratos.model.CategoryEntity;
import com.technokratos.model.EmbeddedUser;
import com.technokratos.model.PostEntity;
import com.technokratos.model.SavedPostEntity;
import com.technokratos.repository.*;
import com.technokratos.util.mapper.PostMapper;
import com.technokratos.util.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    private final PostRepositoryCustom postRepositoryCustom;
    private final SavedPostRepositoryCustom savedPostRepositoryCustom;


    public List<PostResponse> getRecommendedPosts(String currentUserId, Pageable pageable) {
        return List.of();//todo после сервиса рекомендаций
    }

    public List<PostResponse> getSavedPostsByUserId(String viewerId, String userId, Pageable pageable) {

        if (!checkPostPrivacy(viewerId, userId, userService.getSavedPhotoVisibility(userId))) {
            throw new ForbiddenServiceException("You don`t have authority to see this post");
        }

        List<String> savedPostIds = savedPostRepositoryCustom.getSavedPostIdsByUserId(userId);

        log.info("saved post ids: {}", savedPostIds);

        List<PostResponse> posts = postRepository.findAllById(savedPostIds)
                .stream()
                .map(postEntity -> postMapper.toPostResponse(
                        postEntity,
                        postEntity.getImageId() + "test.jpg", //todo сделать получение из минио
                        new UserCompactResponse(
                                UUID.fromString(postEntity.getUser().getUserId()),
                                postEntity.getUser().getUsername(),
                                postEntity.getUser().getAvatarId() + "test.jpg") //todo сделать получение из минио
                ))
                .toList();

        return posts;//todo сделать правильную пагинацию
    }

    public List<PostResponse> getPostsByUserId(String viewerId, String userId, Pageable pageable) {

        log.info("service getPostsByUserId data: viewerId: {}, userId: {}, myPhotoVisibility: {}",
                viewerId, userId, userService.getMyPhotoVisibility(userId));

        if (!checkPostPrivacy(viewerId, userId, userService.getMyPhotoVisibility(userId))) {
            throw new ForbiddenServiceException("You don`t have authority to see this post");
        }

        List<PostResponse> posts = postRepository.findByUser_UserId(userId, pageable)
                .stream()
                .map(postEntity -> postMapper.toPostResponse(
                        postEntity,
                        postEntity.getImageId() + "test.jpg", //todo сделать получение из минио
                        new UserCompactResponse(
                                UUID.fromString(postEntity.getUser().getUserId()),
                                postEntity.getUser().getUsername(),
                                postEntity.getUser().getAvatarId() + "test.jpg") //todo сделать получение из минио
                ))
                .toList();;

        return posts;//todo сделать правильную пагинацию
    }

    public PostResponse getPostById(String viewerId, String postId) {

        PostEntity post = postRepository.findById(postId)
                .orElseThrow(() -> new PostByIdNotFoundException(postId));
        String userId = post.getUser().getUserId();

        if (!checkPostPrivacy(viewerId, userId, userService.getMyPhotoVisibility(userId))) {
            throw new ForbiddenServiceException("You don`t have authority to see this post");
        }

        return postMapper.toPostResponse(post, post.getImageId() + "test.jpg", //todo сделать получение из минио
                new UserCompactResponse(
                        UUID.fromString(post.getUser().getUserId()),
                        post.getUser().getUsername(),
                        post.getUser().getAvatarId() + "test.jpg"));//todo сделать получение фото из минио по id и отдачу url
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

        return postMapper.toPostResponse(post, post.getImageId() + "test.jpg", //todo сделать получение из минио
                new UserCompactResponse(
                        UUID.fromString(post.getUser().getUserId()),
                        post.getUser().getUsername(),
                        post.getUser().getAvatarId() + "test.jpg"));//todo сделать получение фото из минио по id и отдачу url
    }

    public PostResponse update(String currentUserId, String postId, PostRequest updatePostRequest) {
        PostEntity post = postRepository.findById(postId).orElseThrow(() -> new PostByIdNotFoundException(postId));

        if (!post.getUser().getUserId().equals(currentUserId)) {
            throw new ForbiddenServiceException("You can't edit this post");
        }

        CategoryEntity category = categoryRepository.findById(updatePostRequest.categoryId())
                .orElseThrow(() -> new CategoryByIdNotFoundException(updatePostRequest.categoryId()));

        post.setTitle(updatePostRequest.title());
        post.setImageId(updatePostRequest.imageId());
        post.setCategory(postMapper.toEmbeddedCategoryEntity(category));

        postRepository.save(post);

        return postMapper.toPostResponse(post, post.getImageId() + "test.jpg", //todo сделать получение из минио
                new UserCompactResponse(
                        UUID.fromString(post.getUser().getUserId()),
                        post.getUser().getUsername(),
                        post.getUser().getAvatarId() + "test.jpg"));//todo сделать получение фото из минио по id и отдачу url
        }

    public void delete(String currentUserId, String postId) {
        postRepository.findById(postId)
                .ifPresent(post -> {
                    if (!post.getUser().getUserId().equals(currentUserId)) {
                        throw new ForbiddenServiceException("You can't delete this post");
                    }
                    postRepository.deleteById(postId);
                    savedPostRepository.deleteByPostId(postId);
                });
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
        PostEntity post = postRepositoryCustom.getLikesCountByPostId(postId);

        if (post == null) {
            throw new PostByIdNotFoundException(postId);
        }

        return post.getLikesCount();
    }

    public boolean checkPostPrivacy(String viewerId, String userId, PhotoVisibility photoVisibility) {
        if (viewerId.equals(userId)) {
            return true;
        }

        boolean check = switch (photoVisibility) {
            case PhotoVisibility.PUBLIC -> true;
            case PhotoVisibility.PRIVATE -> false;
            case PhotoVisibility.FRIENDS_ONLY -> userService.getUserFriend(userId).contains(viewerId);
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
        PostEntity post = postRepositoryCustom.getUserIdByPostId(postId);

        if (post == null) {
            throw new PostByIdNotFoundException(postId);
        }

        return post.getUser().getUserId();
    }
}

