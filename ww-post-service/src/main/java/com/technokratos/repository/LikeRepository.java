package com.technokratos.repository;

import com.technokratos.model.LikeEntity;
import com.technokratos.model.PostEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeRepository extends MongoRepository<LikeEntity, String> {
    Page<LikeEntity> findByPostId(String postId, Pageable pageable);
}
