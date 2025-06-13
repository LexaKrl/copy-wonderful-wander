package com.technokratos.repository;

import com.technokratos.model.LikeEntity;
import com.technokratos.model.PostEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface LikeRepository extends MongoRepository<LikeEntity, String> {
    List<LikeEntity> findByPostId(String postId);
}
