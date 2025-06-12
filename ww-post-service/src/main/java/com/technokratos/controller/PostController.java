package com.technokratos.controller;

import com.technokratos.api.PostApi;
import com.technokratos.dto.request.post.PostRequest;
import com.technokratos.dto.response.post.PostResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;
import com.technokratos.service.PostService;

import java.util.List;
import java.util.UUID;


@RestController
@RequiredArgsConstructor
@Slf4j
public class PostController implements PostApi {

    private final PostService postService;


    @Override
    public List<PostResponse> getRecommendedPosts(Pageable pageable) {
        return postService.getRecommendedPosts(getCurrentUserId(), pageable);
    }

    @Override
    public List<PostResponse> getCurrentUserPosts(Pageable pageable) {
        return postService.getPostsByUserId(getCurrentUserId(), getCurrentUserId(), pageable);
    }

    @Override
    public List<PostResponse> getCurrentUserSavedPosts(Pageable pageable) {
        return postService.getSavedPostsByUserId(getCurrentUserId(), getCurrentUserId(), pageable);
    }

    @Override
    public List<PostResponse> getPostsByUserId(String userId, Pageable pageable) {
        log.info("controller getPostsByUserId, data: {}", userId);
        return postService.getPostsByUserId(getCurrentUserId(), userId, pageable);
    }

    @Override
    public List<PostResponse> getSavedPostsByUserId(String userId, Pageable pageable) {
        return postService.getSavedPostsByUserId(getCurrentUserId(), userId, pageable);
    }

    @Override
    public PostResponse getPostById(String postId) {
        return postService.getPostById(getCurrentUserId(), postId);
    }

    @Override
    public PostResponse createPost(PostRequest createPostRequest) {
        return postService.create(getCurrentUserId(), createPostRequest);
    }

    @Override
    public PostResponse updatePost(String postId, PostRequest updatePostRequest) {
        return postService.update(getCurrentUserId(), postId, updatePostRequest);
    }

    @Override
    public void deletePost(String postId) {
        postService.delete(getCurrentUserId(), postId);
    }

    @Override
    public void savePost(String postId) {
        postService.savePost(getCurrentUserId(), postId);
    }

    @Override
    public void deleteSavedPost(String postId) {
        postService.deleteSavedPost(getCurrentUserId(), postId);
    }

    private String getCurrentUserId() {
        return "00000000-0000-0000-0000-000000000001";
    }
}
