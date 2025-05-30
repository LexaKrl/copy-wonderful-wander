package com.technokratos.service;

import com.technokratos.dto.request.user.UserRequest;
import com.technokratos.dto.response.user.UserCompactResponse;
import com.technokratos.dto.response.user.UserProfileResponse;
import com.technokratos.dto.response.user.UserResponse;
import com.technokratos.exception.ConflictServiceException;
import com.technokratos.exception.FollowConflictException;
import com.technokratos.exception.UserByIdNotFoundException;
import com.technokratos.exception.UserByUsernameNotFoundException;
import com.technokratos.producer.UserEventProducer;
import com.technokratos.repository.UserRepository;
import com.technokratos.tables.pojos.Account;
import com.technokratos.util.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final MinioService minioService;
    private final UserEventProducer userEventProducer;

    public UserResponse getUserById(UUID userId) {
        Account account = userRepository.findById(userId).orElseThrow(() -> new UserByIdNotFoundException(userId));
        String avatarUrl = minioService.getPresignedUrl(account.getAvatarFilename());
        return userMapper.toUserResponse(userRepository
                        .findById(userId)
                        .orElseThrow(() -> new UserByIdNotFoundException(userId)),
                avatarUrl);
    }

    public UserResponse getUserByUsername(String username) {
        return userMapper.toUserResponse(userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UserByUsernameNotFoundException(username)));
    }

    public List<UserCompactResponse> getFriendsByUserId(UUID userId, Pageable pageable) {
        if (checkUserNotExists(userId)) throw new UserByIdNotFoundException(userId);
        return userMapper.toUserCompactResponse(userRepository.getFriendsByUserId(userId, pageable));
    }

    public List<UserCompactResponse> getFollowersByUserId(UUID userId, Pageable pageable) {
        if (checkUserNotExists(userId)) throw new UserByIdNotFoundException(userId);
        return userMapper.toUserCompactResponse(userRepository.getFollowersByUserId(userId, pageable));
    }

    public List<UserCompactResponse> getFollowingByUserId(UUID userId, Pageable pageable) {
        if (checkUserNotExists(userId)) throw new UserByIdNotFoundException(userId);
        return userMapper.toUserCompactResponse(userRepository.getFollowingByUserId(userId, pageable));
    }

    public void follow(UUID userId, UUID targetUserId) {
        if (checkUserNotExists(userId)) throw new UserByIdNotFoundException(userId);
        if (checkUserNotExists(targetUserId)) throw new UserByIdNotFoundException(targetUserId);
        if (userId.equals(targetUserId)) throw new FollowConflictException("You can't follow to yourself");
        if (checkOnFollow(userId, targetUserId))
            throw new FollowConflictException("Follow already exists from user with id = %s to user with id = %s".formatted(userId, targetUserId));
        userRepository.follow(userId, targetUserId);
    }

    public void unfollow(UUID userId, UUID targetUserId) {
        if (checkUserNotExists(userId)) throw new UserByIdNotFoundException(userId);
        if (checkUserNotExists(targetUserId)) throw new UserByIdNotFoundException(targetUserId);
        if (userId.equals(targetUserId)) throw new FollowConflictException("You can't unfollow from yourself");
        userRepository.unfollow(userId, targetUserId);
    }

    public UserProfileResponse getProfileByUserId(UUID userId, UUID targetUserId) {
        if (targetUserId.equals(userId))
            throw new ConflictServiceException("You can't get profile information as its owner, use a different method");
        Account account = userRepository.findById(targetUserId)
                .orElseThrow(() -> new UserByIdNotFoundException(targetUserId));
        String avatarUrl = minioService.getPresignedUrl(account.getAvatarFilename());
        boolean isFollowed = checkOnFollow(userId, targetUserId);
        boolean isFollowing = checkOnFollow(targetUserId, userId);
        boolean isFriends = isFollowed && isFollowing;
        return userMapper.toUserProfileResponse(account, isFollowed, isFollowing, isFriends, avatarUrl);
    }

    public UserResponse updateUser(UUID userId, UserRequest userRequest) {
        Account user = userRepository
                .update(userId, userRequest)
                .orElseThrow(() -> new UserByIdNotFoundException(userId));
        userEventProducer.sendUserUpdatedEvent(userMapper.toUserUpdatedEvent(user));
        return userMapper.toUserResponse(user);
    }

    public void deleteUser(UUID userId) {
        if (checkUserNotExists(userId)) throw new UserByIdNotFoundException(userId);
        userRepository.delete(userId);
        userEventProducer.sendUserDeletedEvent(userMapper.toUserDeletedEvent(userId));
    }

    public void saveAvatarFilename(UUID userId, String filename) {
        userRepository.saveFilenameByUserId(userId, filename);
    }

    private boolean checkUserNotExists(UUID userId) {
        return !userRepository.existsById(userId);
    }

    private boolean checkOnFollow(UUID userId, UUID targetId) {
        return userRepository.existsFollow(userId, targetId);
    }
}