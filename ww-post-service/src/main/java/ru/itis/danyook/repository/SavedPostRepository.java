package ru.itis.danyook.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import ru.itis.danyook.model.SavedPostEntity;


import java.util.List;
import java.util.UUID;

public interface SavedPostRepository extends MongoRepository<SavedPostEntity, UUID> {
    List<UUID> findPostIdByUserId(UUID userId, Pageable pageable);

    void deleteByPostId(UUID postId);

    void deleteByUserIdAndPostId(UUID userId, UUID postId);

}
