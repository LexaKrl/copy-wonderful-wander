package com.technokratos.service;

import com.technokratos.dto.request.post.PostRequest;
import com.technokratos.dto.response.post.PostResponse;
import com.technokratos.enums.user.PhotoVisibility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.technokratos.exception.CategoryByIdNotFoundException;
import com.technokratos.exception.ForbiddenServiceException;
import com.technokratos.exception.PostByIdNotFoundException;
import com.technokratos.model.*;
import com.technokratos.repository.CategoryRepository;
import com.technokratos.repository.PostRepository;
import com.technokratos.repository.SavedPostRepository;
import com.technokratos.util.mapper.PostMapper;
import com.technokratos.util.mapper.UserMapper;

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


    public List<PostResponse> getRecommendedPosts(UUID currentUserId, Pageable pageable) {
        return List.of();//todo после сервиса рекомендаций
    }

    public List<PostResponse> getSavedPostsByUserId(UUID viewerId, UUID userId, Pageable pageable) {

        PhotoVisibility savedPhotoVisibility = userService.getSavedPhotoVisibility(userId);

        if (!checkPostPrivacy(viewerId, userId, savedPhotoVisibility)) {
            throw new ForbiddenServiceException("You don`t have authority to see this post");
        }//todo проверить

        List<UUID> savedPostIds = savedPostRepository.findPostIdByUserId(userId, pageable);

        List<PostEntity> posts = postRepository.findAllById(savedPostIds);

        return postMapper.toPostResponse(posts);//todo сделать получение фото из минио по id и отдачу url
    }

    public List<PostResponse> getPostsByUserId(UUID viewerId, UUID userId, Pageable pageable) {

        PhotoVisibility myPhotoVisibility = userService.getMyPhotoVisibility(userId);
        log.info("service getPostsByUserId data: viewerId: {}, userId: {}, myPhotoVisibility: {}",
                viewerId, userId, myPhotoVisibility);
        if (!checkPostPrivacy(viewerId, userId, myPhotoVisibility)) {
            throw new ForbiddenServiceException("You don`t have authority to see this post");
        }//todo проверить

        List<PostEntity> posts = postRepository.findByUser_UserId(userId, pageable);

        return postMapper.toPostResponse(posts);//todo сделать получение фото из минио по id и отдачу url
    }

    public PostResponse getPostById(UUID viewerId, UUID postId) {

        PostEntity post = postRepository.findById(postId)
                .orElseThrow(() -> new PostByIdNotFoundException(postId));
        UUID userId = post.getUser().getUserId();

        PhotoVisibility savedPhotoVisibility = userService.getSavedPhotoVisibility(userId);

        if (!checkPostPrivacy(viewerId, userId, savedPhotoVisibility)) {
            throw new ForbiddenServiceException("You don`t have authority to see this post");
        }//todo проверить

        return postMapper.toPostResponse(post);//todo сделать получение фото из минио по id и отдачу url
    }

    public PostResponse create(UUID currentUserId, PostRequest createPostRequest) {
        CachedUserEntity cachedUser = userService.getUserById(currentUserId);

        EmbeddedUser user = userMapper.toEmbeddedUserEntity(cachedUser);
        PostEntity post = postMapper.toPostEntity(createPostRequest);

        CategoryEntity category = categoryRepository.findById(createPostRequest.categoryId())
                .orElseThrow(() -> new CategoryByIdNotFoundException(createPostRequest.categoryId()));

        post.setPostId(UUID.randomUUID());
        post.setUser(user);
        post.setCreatedAt(LocalDateTime.now());
        post.setCategory(postMapper.toEmbeddedCategoryEntity(category));

        postRepository.save(post);

        return postMapper.toPostResponse(post);//todo сделать получение фото из минио по id и отдачу url
    }

    public PostResponse update(UUID currentUserId, UUID postId, PostRequest updatePostRequest) {
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

        return postMapper.toPostResponse(post);//todo сделать получение фото из минио по id и отдачу url
        }

    public void delete(UUID currentUserId, UUID postId) {
        postRepository.findById(postId)
                .ifPresent(post -> {
                    if (!post.getUser().getUserId().equals(currentUserId)) {
                        throw new ForbiddenServiceException("You can't delete this post");
                    }
                    postRepository.deleteById(postId);
                    savedPostRepository.deleteByPostId(postId);
                });
    }

    public void savePost(UUID currentUserId, UUID postId) {
        SavedPostEntity savedPost = new SavedPostEntity();

        savedPost.setId(UUID.randomUUID());
        savedPost.setPostId(postId);
        savedPost.setUserId(currentUserId);
        savedPost.setSavedAt(LocalDateTime.now());

        savedPostRepository.save(savedPost);
    }

    public void deleteSavedPost(UUID currentUserId, UUID postId) {
        savedPostRepository.deleteByUserIdAndPostId(currentUserId, postId);
    }

    private boolean checkPostPrivacy(UUID viewerId, UUID userId, PhotoVisibility photoVisibility) {
        if (viewerId.equals(userId)) {
            return true;
        }

        boolean check = switch (photoVisibility) {
            case PhotoVisibility.PUBLIC -> true;
            case PhotoVisibility.PRIVATE -> viewerId.equals(userId);
            case PhotoVisibility.FRIENDS_ONLY -> userService.getUserFriend(userId).contains(viewerId);
        };
        log.info("checkPostPrivacy: {}, photoVisibility: {}", check, photoVisibility);
        return check;
    }
}

