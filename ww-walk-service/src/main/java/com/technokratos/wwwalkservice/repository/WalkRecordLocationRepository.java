package com.technokratos.wwwalkservice.repository;

import com.technokratos.wwwalkservice.entity.WalkLocationData;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface WalkRecordLocationRepository extends MongoRepository<WalkLocationData, UUID> {
}
