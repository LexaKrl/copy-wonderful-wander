package ru.itis.danyook.controller;

import com.technokratos.api.LikeApi;
import com.technokratos.dto.response.post.UserLikeResponse;
import com.technokratos.dto.response.post.PostResponse;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;


@RestController
public class LikeController implements LikeApi {
    @Override
    public List<UserLikeResponse> getLikesByPostId(UUID postId) {
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
