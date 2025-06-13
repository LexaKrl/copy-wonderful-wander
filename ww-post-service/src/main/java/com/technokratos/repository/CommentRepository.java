package com.technokratos.repository;

import com.technokratos.model.CommentEntity;
import com.technokratos.model.LikeEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends MongoRepository<CommentEntity, String> {
}
