package com.technokratos.service;

import com.technokratos.enums.user.PhotoVisibility;
import com.technokratos.repository.CachedUserRepositoryCustom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.technokratos.model.CachedUserEntity;
import com.technokratos.repository.CachedUserRepository;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final CachedUserRepository cachedUserRepository;
    private final CachedUserRepositoryCustom cachedUserRepositoryCustom;

    public CachedUserEntity getUserById(String userId) {
//        return cachedUserRepository.findById(userId)
//                .orElseGet(() -> uploadUser(userId));

        return cachedUserRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User Not found"));
    }

    public PhotoVisibility getMyPhotoVisibility(String userId) {
        CachedUserEntity user = cachedUserRepositoryCustom.getMyPhotoVisibility(userId);

        log.info("getMyPhotoVisibility data: {}", user);

        if (user == null) {
            user = uploadUser(userId);
        }

        return user.getMyPhotoVisibility();
    }

    public PhotoVisibility getSavedPhotoVisibility(String userId) {
        CachedUserEntity user = cachedUserRepositoryCustom.getSavedPhotoVisibility(userId);

        log.info("getSavedPhotoVisibility data: {}", user);

        if (user == null) {
            user = uploadUser(userId);
        }

        return user.getSavedPhotoVisibility();
    }

    public Set<String> getUserFriend(String userId) {
        CachedUserEntity user = cachedUserRepositoryCustom.getUserFriend(userId);

        log.info("get user friends data: {}", user);

        if (user == null) {
            user = uploadUser(userId);
        }

        return user.getFriends();
    }

    private CachedUserEntity uploadUser(String userId) {
        return cachedUserRepository.save(new CachedUserEntity(
                userId,
                "danyo_ok",
                "123.jpg",
                PhotoVisibility.PUBLIC,
                PhotoVisibility.PUBLIC,
                new HashSet<>())); //todo сделать загрузку через фейн клиент
    }
}
