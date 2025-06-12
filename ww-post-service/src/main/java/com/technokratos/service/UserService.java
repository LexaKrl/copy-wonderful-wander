package com.technokratos.service;

import com.technokratos.enums.user.PhotoVisibility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.technokratos.model.CachedUserEntity;
import com.technokratos.repository.CachedUserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final CachedUserRepository cachedUserRepository;

    public CachedUserEntity getUserById(String userId) {
//        return cachedUserRepository.findById(userId)
//                .orElseGet(() -> {
//                    String username = "danyo_ok";//todo
//                    String avatarId = "123.jpg";//todo
//                    return cachedUserRepository.save(new CachedUserEntity(
//                            userId,
//                            username,
//                            avatarId,
//                            PhotoVisibility.PRIVATE,
//                            PhotoVisibility.PRIVATE,
//                            new HashSet<>()));
//                    //todo посмотреть как получить из user service(наверное написать клиента который будет получать данные из юзер сервиса
//                });

        return cachedUserRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User Not found"));
    }

    public PhotoVisibility getMyPhotoVisibility(String userId) {
        return getUserById(userId).getMyPhotoVisibility();
    }

    public PhotoVisibility getSavedPhotoVisibility(String userId) {
        return getUserById(userId).getSavedPhotoVisibility();
    }

    public Set<String> getUserFriend(String userId) {
        return cachedUserRepository.findFriendsByUserId(userId)
                .getFriends();
    }
}
