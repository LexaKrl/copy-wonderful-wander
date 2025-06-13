package com.technokratos.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.technokratos.model.SavedPostEntity;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.UUID;

@Repository
public interface SavedPostRepository extends MongoRepository<SavedPostEntity, String> {
    void deleteByPostId(String postId);
    void deleteByUserIdAndPostId(String userId, String postId);
    boolean existsByUserIdAndPostId(String userId, String postId);

}
