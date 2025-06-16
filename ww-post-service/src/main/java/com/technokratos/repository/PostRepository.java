package com.technokratos.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.stereotype.Repository;
import com.technokratos.model.PostEntity;

import java.util.List;
import java.util.UUID;

@Repository
public interface PostRepository extends MongoRepository<PostEntity, String> {
    Page<PostEntity> findByUser_UserId(String userId, Pageable pageable);

    Page<PostEntity> findAllByPostIdIn(List<String> postId, Pageable pageable);
}
