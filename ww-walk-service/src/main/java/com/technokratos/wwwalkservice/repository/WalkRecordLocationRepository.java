package com.technokratos.wwwalkservice.repository;

import com.technokratos.wwwalkservice.entity.WalkLocationData;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
import java.util.UUID;

public interface WalkRecordLocationRepository extends MongoRepository<WalkLocationData, UUID> {
    Optional<WalkLocationData> findByWalkId(UUID walkId);
}
