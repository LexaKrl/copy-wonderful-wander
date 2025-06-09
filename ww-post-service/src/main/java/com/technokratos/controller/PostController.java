package com.technokratos.controller;

import com.technokratos.api.PostApi;
import com.technokratos.dto.request.post.PostRequest;
import com.technokratos.dto.response.post.PostResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;
import com.technokratos.service.PostService;

import java.util.List;
import java.util.UUID;


@RestController
@RequiredArgsConstructor
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
    public List<PostResponse> getPostsByUserId(UUID userId, Pageable pageable) {
        return postService.getPostsByUserId(getCurrentUserId(), userId, pageable);
    }

    @Override
    public List<PostResponse> getSavedPostsByUserId(UUID userId, Pageable pageable) {
        return postService.getSavedPostsByUserId(getCurrentUserId(), userId, pageable);
    }

    @Override
    public PostResponse getPostById(UUID postId) {
        return postService.getPostById(getCurrentUserId(), postId);
    }

    @Override
    public PostResponse createPost(PostRequest createPostRequest) {
        return postService.create(getCurrentUserId(), createPostRequest);
    }

    @Override
    public PostResponse updatePost(UUID postId, PostRequest updatePostRequest) {
        return postService.update(getCurrentUserId(), postId, updatePostRequest);
    }

    @Override
    public void deletePost(UUID postId) {
        postService.delete(getCurrentUserId(), postId);
    }

    @Override
    public void savePost(UUID postId) {
        postService.savePost(getCurrentUserId(), postId);
    }

    @Override
    public void deleteSavedPost(UUID postId) {
        postService.deleteSavedPost(getCurrentUserId(), postId);
    }

    private UUID getCurrentUserId() {
        return UUID.fromString("077a25a8-1fda-4ced-82f6-24b03b326c1c");
    }
}
