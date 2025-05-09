package com.technokratos.api;

import com.technokratos.dto.request.user.UserRequest;
import com.technokratos.dto.response.user.UserProfileResponse;
import com.technokratos.dto.response.user.UserResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("/api/users")
public interface UserApi {
    @GetMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    UserResponse getCurrentUserProfile();

    @PutMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    UserResponse updateCurrentUser(@RequestBody @Validated UserRequest userRequest);

    @DeleteMapping("/me")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteCurrentUser();

    @PostMapping("/me/follows/{targetUserId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void follow(@PathVariable UUID targetUserId);

    @DeleteMapping("/me/follows/{targetUserId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void unfollow(@PathVariable UUID targetUserId);

    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    UserProfileResponse getUserProfileById(@PathVariable UUID userId);

    @GetMapping("/{userId}/friends")
    @ResponseStatus(HttpStatus.OK)
    List<UserProfileResponse> getFriendsByUserId(@PathVariable UUID userId, Pageable pageable);

    @GetMapping("/{userId}/following")
    @ResponseStatus(HttpStatus.OK)
    List<UserProfileResponse> getFollowingByUserId(@PathVariable UUID userId, Pageable pageable);

    @GetMapping("/{userId}/followers")
    @ResponseStatus(HttpStatus.OK)
    List<UserProfileResponse> getFollowersByUserId(@PathVariable UUID userId, Pageable pageable);
}