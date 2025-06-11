package com.technokratos.repository;

import com.technokratos.enums.user.PhotoVisibility;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.technokratos.model.CachedUserEntity;
import org.springframework.data.mongodb.repository.Query;

import java.util.Set;
import java.util.UUID;

public interface CachedUserRepository extends MongoRepository<CachedUserEntity, UUID> {
    PhotoVisibility findMyPhotoVisibilityByUserId(UUID userId);
    PhotoVisibility findSavedPhotoVisibilityByUserId(UUID userId);

    @Aggregation(pipeline = {
            "{ $match: { 'userId' : ?0 } }",
            "{ $project: { _id: 0, friends: 1 } }",
            "{ $unwind: '$friends' }",
            "{ $group: { _id: null, friends: { $addToSet: '$friends' } } }"
    })
    Set<UUID> findFriendsByUserId(UUID userId);
}
