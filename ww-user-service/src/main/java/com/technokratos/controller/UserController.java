package com.technokratos.controller;

import com.technokratos.api.UserApi;
import com.technokratos.dto.request.user.UserRequest;
import com.technokratos.dto.response.PageResponse;
import com.technokratos.dto.response.user.UserCompactResponse;
import com.technokratos.dto.response.user.UserProfileResponse;
import com.technokratos.dto.response.user.UserResponse;
import com.technokratos.security.BaseUserContextHolder;
import com.technokratos.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class UserController implements UserApi {
    private final UserService userService;
    private final BaseUserContextHolder userContextHolder;

    @Override
    public UserResponse getCurrentUserProfile(UUID ownerId) {
        return userService.getUserById(ownerId);
    }

    @Override
    public UserResponse updateCurrentUser(UUID ownerId, UserRequest userRequest) {
        return userService.updateUser(ownerId, userRequest);
    }

    @Override
    public void deleteCurrentUser(UUID ownerId) {
        userService.deleteUser(ownerId);
    }

    @Override
    public void follow(UUID ownerId, UUID targetUserId) {
        userService.follow(ownerId, targetUserId);
    }

    @Override
    public void unfollow(UUID ownerId, UUID targetUserId) {
        userService.unfollow(ownerId, targetUserId);
    }

    @Override
    public UserProfileResponse getUserProfileById(UUID ownerId, UUID targetUserId) {
        return userService.getProfileByUserId(ownerId, targetUserId);
    }

    @Override
    public PageResponse<UserCompactResponse> getFriendsByUserId(UUID userId, Integer page, Integer size) {
        return userService.getFriendsByUserId(userId, page, size);
    }

    @Override
    public PageResponse<UserCompactResponse> getFollowingByUserId(UUID userId, Integer page, Integer size) {
        return userService.getFollowingByUserId(userId, page, size);
    }

    @Override
    public PageResponse<UserCompactResponse> getFollowersByUserId(UUID userId, Integer page, Integer size) {
        return userService.getFollowersByUserId(userId, page, size);
    }
}