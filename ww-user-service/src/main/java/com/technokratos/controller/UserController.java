package com.technokratos.controller;

import com.technokratos.api.UserApi;
import com.technokratos.dto.request.user.UserRequest;
import com.technokratos.dto.response.user.UserCompactResponse;
import com.technokratos.dto.response.user.UserProfileResponse;
import com.technokratos.dto.response.user.UserResponse;
import com.technokratos.model.UserEntity;
import com.technokratos.security.BaseUserContextHolder;
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
    private final BaseUserContextHolder userContextHolder;

    @Override
    public UserResponse getCurrentUserProfile() {
        return userService.getUserById(getCurrentUserId());
    }

    @Override
    public UserResponse updateCurrentUser(UserRequest userRequest) {
        return userService.updateUser(getCurrentUserId(), userRequest);
    }

    @Override
    public void deleteCurrentUser() {
        userService.deleteUser(getCurrentUserId());
    }

    @Override
    public void follow(UUID targetUserId) {
        userService.follow(getCurrentUserId(), targetUserId);
    }

    @Override
    public void unfollow(UUID targetUserId) {
        userService.unfollow(getCurrentUserId(), targetUserId);
    }

    @Override
    public UserProfileResponse getUserProfileById(UUID targetUserId) {
        return userService.getProfileByUserId(getCurrentUserId(), targetUserId);
    }

    @Override
    public List<UserCompactResponse> getFriendsByUserId(UUID userId, Pageable pageable) {
        return userService.getFriendsByUserId(userId, pageable);
    }

    @Override
    public List<UserCompactResponse> getFollowingByUserId(UUID userId, Pageable pageable) {
        return userService.getFollowingByUserId(userId, pageable);
    }

    @Override
    public List<UserCompactResponse> getFollowersByUserId(UUID userId, Pageable pageable) {
        return userService.getFollowersByUserId(userId, pageable);
    }

    private UUID getCurrentUserId() {
        UserEntity userFromSecurityContext = userContextHolder.getUserFromSecurityContext();
        return userFromSecurityContext.getUserId();
    }
}