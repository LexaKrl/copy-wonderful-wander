package com.technokratos.repository;

import com.technokratos.enums.user.PhotoVisibility;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.technokratos.model.CachedUserEntity;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface CachedUserRepository extends MongoRepository<CachedUserEntity, String> {
}
