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
import java.util.stream.Collectors;

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


    public List<PostResponse> getRecommendedPosts(String currentUserId, Pageable pageable) {
        return List.of();//todo после сервиса рекомендаций
    }

    public List<PostResponse> getSavedPostsByUserId(String viewerId, String userId, Pageable pageable) {

        PhotoVisibility savedPhotoVisibility = userService.getSavedPhotoVisibility(userId);

        if (!checkPostPrivacy(viewerId, userId, savedPhotoVisibility)) {
            throw new ForbiddenServiceException("You don`t have authority to see this post");
        }

        List<String> savedPostIds = savedPostRepository.findPostIdByUserId(userId, pageable)
                .stream()
                .map(projection ->
                        projection.getPostId()).collect(Collectors.toList());

        List<PostEntity> posts = postRepository.findAllById(savedPostIds);

        return postMapper.toPostResponse(posts);//todo сделать получение фото из минио по id и отдачу url
    }

    public List<PostResponse> getPostsByUserId(String viewerId, String userId, Pageable pageable) {

        PhotoVisibility myPhotoVisibility = userService.getMyPhotoVisibility(userId);
        log.info("service getPostsByUserId data: viewerId: {}, userId: {}, myPhotoVisibility: {}",
                viewerId, userId, myPhotoVisibility);
        if (!checkPostPrivacy(viewerId, userId, myPhotoVisibility)) {
            throw new ForbiddenServiceException("You don`t have authority to see this post");
        }

        List<PostEntity> posts = postRepository.findByUser_UserId(userId, pageable);

        return postMapper.toPostResponse(posts);//todo сделать получение фото из минио по id и отдачу url
    }

    public PostResponse getPostById(String viewerId, String postId) {

        PostEntity post = postRepository.findById(postId)
                .orElseThrow(() -> new PostByIdNotFoundException(postId));
        String userId = post.getUser().getUserId();

        PhotoVisibility myPhotoVisibility = userService.getMyPhotoVisibility(userId);

        if (!checkPostPrivacy(viewerId, userId, myPhotoVisibility)) {
            throw new ForbiddenServiceException("You don`t have authority to see this post");
        }

        return postMapper.toPostResponse(post);//todo сделать получение фото из минио по id и отдачу url
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

        return postMapper.toPostResponse(post);//todo сделать получение фото из минио по id и отдачу url
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

        return postMapper.toPostResponse(post);//todo сделать получение фото из минио по id и отдачу url
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

    public void savePost(String currentUserId, String postId) {


        PostEntity post = postRepository.findById(postId)
                .orElseThrow(() -> new PostByIdNotFoundException(postId));
        String userId = post.getUser().getUserId();

        PhotoVisibility myPhotoVisibility =  userService.getMyPhotoVisibility(userId);

        if (!checkPostPrivacy(currentUserId, userId, myPhotoVisibility)) {
            throw new ForbiddenServiceException("You don`t have authority to save this post");
        }

        SavedPostEntity savedPost = new SavedPostEntity();

        savedPost.setSavedPostId(String.valueOf(UUID.randomUUID()));
        savedPost.setPostId(postId);
        savedPost.setUserId(currentUserId);
        savedPost.setSavedAt(LocalDateTime.now());

        log.info("saved post: {}", savedPost);

        log.info("save post service: {}", savedPostRepository.save(savedPost));
    }

    public void deleteSavedPost(String currentUserId, String postId) {
        savedPostRepository.deleteByUserIdAndPostId(currentUserId, postId);
    }

    private boolean checkPostPrivacy(String viewerId, String userId, PhotoVisibility photoVisibility) {
        if (viewerId.equals(userId)) {
            return true;
        }

        boolean check = switch (photoVisibility) {
            case PhotoVisibility.PUBLIC -> true;
            case PhotoVisibility.PRIVATE -> viewerId.equals(userId);
            case PhotoVisibility.FRIENDS_ONLY -> userService.getUserFriend(userId).contains(viewerId);
        };
        log.info("checkPostPrivacy: {}, photoVisibility: {}", check, photoVisibility);
        log.info("view id: {}", viewerId);
        log.info("user id: {}", userId);
        return check;
    }
}

