package com.technokratos.service;

import com.technokratos.enums.user.PhotoVisibility;
import com.technokratos.model.*;
import com.technokratos.repository.UserFriendRepository;
import com.technokratos.repository.custom.CustomCachedUserRepository;
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

    public CachedUserEntity getUserById(String userId) {
//        return cachedUserRepository.findById(userId)
//                .orElseGet(() -> uploadUser(userId));

        return cachedUserRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User Not found"));
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

//    public Set<String> getUserFriend(String userId) {
//        List<UserFriendEntity> friendsEntities = customCachedUserRepository.getUserFriend(userId);
//
//        log.info("get user friends data: {}", friendsEntities);
//
//        if (friendsEntities == null) {
//            user = uploadUser(userId);
//        }
//
//        return user.getFriends();
//    }

    public boolean isFriends(String userId1, String userId2) {
        return customCachedUserRepository.isFriends(userId1, userId2);
    }

    private CachedUserEntity uploadUser(String userId) {
        return cachedUserRepository.save(new CachedUserEntity(
                userId,
                "danyo_ok",
                "123.jpg",
                PhotoVisibility.PUBLIC,
                PhotoVisibility.PUBLIC)); //todo сделать загрузку через фейн клиент
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
