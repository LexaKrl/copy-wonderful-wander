package com.technokratos.repository.custom;

import com.technokratos.model.PostEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CustomPostRepository {

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

    public void incrementLikesCount(String postId) {
        mongoTemplate.updateFirst(
                Query.query(Criteria.where("postId").is(postId)),
                new Update().inc("likesCount", 1),
                PostEntity.class
        );
    }

    public void decrementLikesCount(String postId) {
        mongoTemplate.updateFirst(
                Query.query(Criteria.where("postId").is(postId)),
                new Update().inc("likesCount", -1),
                PostEntity.class
        );
    }

    public PostEntity getCommentsCountByPostId(String postId) {
        Query query = new Query(Criteria.where("postId").is(postId));
        query.fields().include("commentsCount");
        return mongoTemplate.findOne(query, PostEntity.class);
    }

    public void incrementCommentsCount(String postId) {
        mongoTemplate.updateFirst(
                Query.query(Criteria.where("postId").is(postId)),
                new Update().inc("commentsCount", 1),
                PostEntity.class
        );
    }

    public void reduceCommentsCount(String postId, Long countToDelete) {
        mongoTemplate.updateFirst(
                Query.query(Criteria.where("postId").is(postId)),
                new Update().inc("commentsCount", -countToDelete),
                PostEntity.class
        );
    }

}
