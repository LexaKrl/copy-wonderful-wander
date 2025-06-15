package com.technokratos.controller;

import com.technokratos.api.LikeApi;
import com.technokratos.dto.response.post.LikeResponse;
import com.technokratos.dto.response.post.PostResponse;
import com.technokratos.dto.response.user.UserCompactResponse;
import com.technokratos.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;


@RestController
@RequiredArgsConstructor
public class LikeController implements LikeApi {

    private final LikeService likeService;

    @Override
    public List<UserCompactResponse> getLikesByPostId(String currentUserId, String postId, Pageable pageable) {
        return likeService.getLikesByPostId(currentUserId, postId);
    }

    @Override
    public LikeResponse createLike(String currentUserId, String postId) {
        return likeService.createLike(currentUserId, postId);
    }

    @Override
    public LikeResponse deleteLike(String currentUserId, String postId) {
        return likeService.deleteLike(currentUserId, postId);
    }
}
