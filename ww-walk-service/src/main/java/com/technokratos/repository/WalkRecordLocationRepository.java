package com.technokratos.repository;

import com.technokratos.entity.WalkLocationData;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
import java.util.UUID;

public interface WalkRecordLocationRepository extends MongoRepository<WalkLocationData, UUID> {
    Optional<WalkLocationData> findByWalkId(UUID walkId);
    void deleteByWalkId(UUID walkId);
}
