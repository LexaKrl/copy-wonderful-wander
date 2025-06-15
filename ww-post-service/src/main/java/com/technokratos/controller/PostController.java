package com.technokratos.controller;

import com.technokratos.api.PostApi;
import com.technokratos.dto.request.post.PostRequest;
import com.technokratos.dto.response.post.PostResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;
import com.technokratos.service.PostService;

import java.awt.*;
import java.util.List;
import java.util.UUID;


@RestController
@RequiredArgsConstructor
@Slf4j
public class PostController implements PostApi {

    private final PostService postService;


    @Override
    public List<PostResponse> getRecommendedPosts(String currentUserId, Pageable pageable) {
        return postService.getRecommendedPosts(currentUserId, pageable);
    }

    @Override
    public List<PostResponse> getCurrentUserPosts(String currentUserId, Pageable pageable) {
        return postService.getPostsByUserId(currentUserId, currentUserId, pageable);
    }

    @Override
    public List<PostResponse> getCurrentUserSavedPosts(String currentUserId, Pageable pageable) {
        return postService.getSavedPostsByUserId(currentUserId, currentUserId, pageable);
    }

    @Override
    public List<PostResponse> getPostsByUserId(String currentUserId, String userId, Pageable pageable) {
        log.info("controller getPostsByUserId, data: {}", userId);
        return postService.getPostsByUserId(currentUserId, userId, pageable);
    }

    @Override
    public List<PostResponse> getSavedPostsByUserId(String currentUserId, String userId, Pageable pageable) {
        return postService.getSavedPostsByUserId(currentUserId, userId, pageable);
    }

    @Override
    public PostResponse getPostById(String currentUserId, String postId) {
        return postService.getPostById(currentUserId, postId);
    }

    @Override
    public PostResponse createPost(String currentUserId, PostRequest createPostRequest) {
        return postService.create(currentUserId, createPostRequest);
    }

    @Override
    public PostResponse updatePost(String currentUserId, String postId, PostRequest updatePostRequest) {
        return postService.update(currentUserId, postId, updatePostRequest);
    }

    @Override
    public void deletePost(String currentUserId, String postId) {
        postService.delete(currentUserId, postId);
    }

    @Override
    public UUID savePost(String currentUserId, String postId) {
        return postService.savePost(currentUserId, postId);
    }

    @Override
    public void deleteSavedPost(String currentUserId, String postId) {
        postService.deleteSavedPost(currentUserId, postId);
    }
}
