package com.technokratos.controller;

import com.technokratos.api.PostApi;
import com.technokratos.dto.request.post.PostRequest;
import com.technokratos.dto.response.PageResponse;
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
    public PageResponse<PostResponse> getRecommendedPosts(String currentUserId, Integer page, Integer size) {
        return postService.getRecommendedPosts(currentUserId, page, size);
    }

    @Override
    public PageResponse<PostResponse> getCurrentUserPosts(String currentUserId, Integer page, Integer size) {
        return postService.getPostsByUserId(currentUserId, currentUserId, page, size);
    }

    @Override
    public PageResponse<PostResponse> getCurrentUserSavedPosts(String currentUserId, Integer page, Integer size) {
        return postService.getSavedPostsByUserId(currentUserId, currentUserId, page, size);
    }

    @Override
    public PageResponse<PostResponse> getPostsByUserId(String currentUserId, String userId, Integer page, Integer size) {
        log.info("controller getPostsByUserId, data: {}", userId);
        return postService.getPostsByUserId(currentUserId, userId, page, size);
    }

    @Override
    public PageResponse<PostResponse> getSavedPostsByUserId(String currentUserId, String userId, Integer page, Integer size) {
        return postService.getSavedPostsByUserId(currentUserId, userId, page, size);
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
