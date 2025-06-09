package com.technokratos.controller;

import com.technokratos.api.LikeApi;
import com.technokratos.dto.response.post.PostResponse;
import com.technokratos.dto.response.user.UserCompactResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;


@RestController
public class LikeController implements LikeApi {

    @Override
    public List<UserCompactResponse> getLikesByPostId(UUID postId, Pageable pageable) {
        return List.of();
    }

    @Override
    public List<PostResponse> createLike(UUID postId) {
        return List.of();
    }

    @Override
    public void deleteLike(UUID postId) {

    }
}
