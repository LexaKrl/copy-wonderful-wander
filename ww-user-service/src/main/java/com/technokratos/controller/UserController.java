package com.technokratos.controller;

import com.technokratos.api.UserApi;
import com.technokratos.dto.request.user.UserRequest;
import com.technokratos.dto.response.user.UserProfileResponse;
import com.technokratos.dto.response.user.UserResponse;
import com.technokratos.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class UserController implements UserApi {
    private final UserService userService;

    @Override
    public UserResponse getCurrentUserProfile() {
        UUID userId = UUID.fromString("1978b53d-d03d-46e3-8a70-9d87468f4677");
        return userService.getUserById(userId);
    }

    @Override
    public UserResponse updateCurrentUser(UserRequest userRequest) {
        UUID userId = UUID.fromString("1978b53d-d03d-46e3-8a70-9d87468f4677");
        return userService.updateUser(userId, userRequest);
    }

    @Override
    public void deleteCurrentUser() {
        UUID userId = UUID.fromString("1978b53d-d03d-46e3-8a70-9d87468f4677");
        userService.deleteUser(userId);
    }

    @Override
    public void follow(UUID targetUserId) {
        UUID userId = UUID.fromString("1978b53d-d03d-46e3-8a70-9d87468f4677");
        userService.follow(userId, targetUserId);
    }

    @Override
    public void unfollow(UUID targetUserId) {
        UUID userId = UUID.fromString("1978b53d-d03d-46e3-8a70-9d87468f4677");
        userService.unfollow(userId, targetUserId);
    }

    @Override
    public UserProfileResponse getUserProfileById(UUID userId) {
        return userService.getProfileByUserId(userId);
    }

    @Override
    public List<UserProfileResponse> getFriendsByUserId(UUID userId, Pageable pageable) {
        return userService.getFriendsByUserId(userId, pageable);
    }

    @Override
    public List<UserProfileResponse> getFollowingByUserId(UUID userId, Pageable pageable) {
        return userService.getFollowingByUserId(userId, pageable);
    }

    @Override
    public List<UserProfileResponse> getFollowersByUserId(UUID userId, Pageable pageable) {
        return userService.getFollowersByUserId(userId, pageable);
    }
}