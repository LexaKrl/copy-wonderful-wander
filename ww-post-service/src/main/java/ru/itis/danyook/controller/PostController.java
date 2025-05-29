package ru.itis.danyook.controller;

import com.technokratos.api.PostApi;
import com.technokratos.dto.request.post.PostRequest;
import com.technokratos.dto.response.post.PostResponse;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;


@RestController
public class PostController implements PostApi {
    @Override
    public List<PostResponse> getRecommendedPosts() {
        return List.of();
    }

    @Override
    public List<PostResponse> getCurrentUserPosts() {
        return List.of();
    }

    @Override
    public List<PostResponse> getCurrentUserSavedPosts() {
        return List.of();
    }

    @Override
    public List<PostResponse> getPostsByUserId(UUID userId) {
        return List.of();
    }

    @Override
    public List<PostResponse> getSavedPostsByUserId(UUID userId) {
        return List.of();
    }

    @Override
    public PostResponse getPostById(UUID postId) {
        return null;
    }

    @Override
    public PostResponse createPost(PostRequest createPostRequest) {
        return null;
    }

    @Override
    public PostResponse updatePost(UUID postId, PostRequest updatePostRequest) {
        return null;
    }

    @Override
    public void deletePost(UUID postId) {

    }
}
