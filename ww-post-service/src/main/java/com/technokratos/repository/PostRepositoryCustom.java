package com.technokratos.repository;

import com.technokratos.exception.PostByIdNotFoundException;
import com.technokratos.model.PostEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PostRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    public PostEntity getLikesCountByPostId(String postId) {
        Query query = new Query(Criteria.where("postId").is(postId));
        query.fields().include("likesCount");
        return mongoTemplate.findOne(query, PostEntity.class);
    }

    public PostEntity getUserIdByPostId(String postId) {

        Query query = new Query(Criteria.where("postId").is(postId));
        query.fields().include("user.userId");

        return mongoTemplate.findOne(query, PostEntity.class);
    }

}
