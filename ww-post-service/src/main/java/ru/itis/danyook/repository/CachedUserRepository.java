package ru.itis.danyook.repository;

import com.technokratos.enums.user.PhotoVisibility;
import org.springframework.data.mongodb.repository.MongoRepository;
import ru.itis.danyook.model.CachedUserEntity;
import ru.itis.danyook.model.PostEntity;

import java.util.Set;
import java.util.UUID;

public interface CachedUserRepository extends MongoRepository<CachedUserEntity, UUID> {
    PhotoVisibility findMyPhotoVisibilityByUserId(UUID userId);
    PhotoVisibility findSavedPhotoVisibilityByUserId(UUID userId);
    Set<UUID> findFriendsByUserId(UUID userId);
}
