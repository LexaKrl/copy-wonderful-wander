package ru.itis.danyook.service;

import com.technokratos.enums.user.PhotoVisibility;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itis.danyook.model.CachedUserEntity;
import ru.itis.danyook.repository.CachedUserRepository;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final CachedUserRepository cachedUserRepository;

    public CachedUserEntity getUserById(UUID userId) {
        return cachedUserRepository.findById(userId)
                .orElseGet(() -> {
                    String username = "danyo_ok";//todo
                    String avatarUrl = "//test";//todo
                    return cachedUserRepository.save(new CachedUserEntity(
                            userId,
                            username,
                            avatarUrl,
                            PhotoVisibility.PRIVATE,
                            PhotoVisibility.PRIVATE,
                            new HashSet<>()));
                    //todo посмотреть как получить из user service(наверное написать клиента который будет получать данные из юзер сервиса
                });
    }

    public PhotoVisibility getMyPhotoVisibility(UUID userId) {
        return getUserById(userId).getMyPhotoVisibility();
    }

    public PhotoVisibility getSavedPhotoVisibility(UUID userId) {
        return getUserById(userId).getSavedPhotoVisibility();
    }

    public Set<UUID> getUserFriend(UUID userId) {
        return cachedUserRepository.findFriendsByUserId(userId);
    }
}
