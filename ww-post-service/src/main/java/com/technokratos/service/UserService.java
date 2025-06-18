package com.technokratos.service;

import com.technokratos.client.UserClient;
import com.technokratos.enums.user.PhotoVisibility;
import com.technokratos.model.*;
import com.technokratos.repository.UserFriendRepository;
import com.technokratos.repository.custom.CustomCachedUserRepository;
import com.technokratos.util.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.stereotype.Service;
import com.technokratos.repository.CachedUserRepository;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final CachedUserRepository cachedUserRepository;
    private final CustomCachedUserRepository customCachedUserRepository;
    private final UserFriendRepository userFriendRepository;
    private final UserClient userClient;
    private final UserMapper userMapper;

    public CachedUserEntity getUserById(String userId) {
        return cachedUserRepository.findById(userId)
                .orElseGet(() -> uploadUser(userId));
    }

    public PhotoVisibility getMyPhotoVisibility(String userId) {
        CachedUserEntity user = customCachedUserRepository.getMyPhotoVisibility(userId);

        log.info("getMyPhotoVisibility data: {}", user);

        if (user == null) {
            user = uploadUser(userId);
        }

        return user.getMyPhotoVisibility();
    }

    public PhotoVisibility getSavedPhotoVisibility(String userId) {
        CachedUserEntity user = customCachedUserRepository.getSavedPhotoVisibility(userId);

        log.info("getSavedPhotoVisibility data: {}", user);

        if (user == null) {
            user = uploadUser(userId);
        }

        return user.getSavedPhotoVisibility();
    }

    public boolean isFriends(String userId1, String userId2) {
        return customCachedUserRepository.isFriends(userId1, userId2);
    }

    private CachedUserEntity uploadUser(String userId) {

        customCachedUserRepository.syncFriendsForUser(userId);

        log.info("userClient.getUsersById(UUID.fromString(userId)): {}", userClient.getUsersById(UUID.fromString(userId)));
        log.info("mapper: {}", userMapper.toCachedUserEntity(
                userClient.getUsersById(UUID.fromString(userId))));

        return cachedUserRepository.save(
                userMapper.toCachedUserEntity(
                        userClient.getUsersById(UUID.fromString(userId)))
        );
    }

    public void save(CachedUserEntity cachedUserEntity) {
        cachedUserRepository.save(cachedUserEntity);
    }

    public void update(CachedUserEntity updatedCachedUserEntity) {
        CachedUserEntity user = getUserById(updatedCachedUserEntity.getUserId());

        user.setMyPhotoVisibility(updatedCachedUserEntity.getMyPhotoVisibility());
        user.setSavedPhotoVisibility(updatedCachedUserEntity.getSavedPhotoVisibility());

        cachedUserRepository.save(user);
    }

    public void updateAvatarUrl(String userId, String newAvatarFilename) {
        CachedUserEntity user = getUserById(userId);

        user.setAvatarFilename(newAvatarFilename);

        cachedUserRepository.save(user);

        customCachedUserRepository.updateEmbeddedUserInCollection(userId, newAvatarFilename, CommentEntity.class);
        customCachedUserRepository.updateEmbeddedUserInCollection(userId, newAvatarFilename, LikeEntity.class);
        customCachedUserRepository.updateEmbeddedUserInCollection(userId, newAvatarFilename, PostEntity.class);
    }

    public void setFriend(String userId, String friendId) {
        UserFriendEntity userFriendEntity = UserFriendEntity.builder()
                .id(String.valueOf(UUID.randomUUID()))
                .userId(userId)
                .friendId(friendId)
                .build();

        userFriendRepository.save(userFriendEntity);
    }

    public void deleteFriend(String userId, String friendId) {
        customCachedUserRepository.deleteFriend(userId, friendId);
    }
}
