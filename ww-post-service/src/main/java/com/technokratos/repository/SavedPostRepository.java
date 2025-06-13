package com.technokratos.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.technokratos.model.SavedPostEntity;
import org.springframework.data.mongodb.repository.Query;


import java.util.List;
import java.util.UUID;

public interface SavedPostRepository extends MongoRepository<SavedPostEntity, String> {
    void deleteByPostId(String postId);
    void deleteByUserIdAndPostId(String userId, String postId);
    boolean existsByUserIdAndPostId(String userId, String postId);

}
