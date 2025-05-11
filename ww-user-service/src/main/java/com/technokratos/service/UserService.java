package com.technokratos.service;

import com.technokratos.dto.request.user.UserRequest;
import com.technokratos.dto.response.user.UserProfileResponse;
import com.technokratos.dto.response.user.UserResponse;
import com.technokratos.exception.UserByIdNotFoundException;
import com.technokratos.exception.UserByUsernameNotFoundException;
import com.technokratos.repository.UserRepository;
import com.technokratos.util.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserResponse getUserById(UUID userId) {
        return userMapper.toUserResponse(userRepository
                .findById(userId)
                .orElseThrow(() -> new UserByIdNotFoundException(userId)));
    }

    public UserResponse getUserByUsername(String username) {
        return userMapper.toUserResponse(userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UserByUsernameNotFoundException(username)));
    }

    public List<UserProfileResponse> getFriendsByUserId(UUID userId, Pageable pageable) {
        if (checkUserNotExists(userId)) throw new UserByIdNotFoundException(userId);
        return userMapper.toUserProfileResponse(userRepository.getFriendsByUserId(userId, pageable));
    }

    public List<UserProfileResponse> getFollowersByUserId(UUID userId, Pageable pageable) {
        if (checkUserNotExists(userId)) throw new UserByIdNotFoundException(userId);
        return userMapper.toUserProfileResponse(userRepository.getFollowersByUserId(userId, pageable));
    }

    public List<UserProfileResponse> getFollowingByUserId(UUID userId, Pageable pageable) {
        if (checkUserNotExists(userId)) throw new UserByIdNotFoundException(userId);
        return userMapper.toUserProfileResponse(userRepository.getFollowingByUserId(userId, pageable));
    }

    public void follow(UUID userId, UUID targetUserId) {
        if (checkUserNotExists(userId)) throw new UserByIdNotFoundException(targetUserId);
        userRepository.follow(userId, targetUserId);
    }

    public void unfollow(UUID userId, UUID targetUserId) {
        if (checkUserNotExists(userId)) throw new UserByIdNotFoundException(targetUserId);
        userRepository.unfollow(userId, targetUserId);
    }

    public UserProfileResponse getProfileByUserId(UUID userId) {
        return userMapper.toUserProfileResponse(userRepository.findById(userId)
                .orElseThrow(() -> new UserByIdNotFoundException(userId)));
    }

    public UserResponse updateUser(UUID userId, UserRequest userRequest) {
        return userMapper.toUserResponse(userRepository
                .update(userId, userRequest)
                .orElseThrow(() -> new UserByIdNotFoundException(userId)));
    }

    public void deleteUser(UUID userId) {
        userRepository.delete(userId);
    }

    private boolean checkUserNotExists(UUID userId) {
        return !userRepository.existsById(userId);
    }

}
