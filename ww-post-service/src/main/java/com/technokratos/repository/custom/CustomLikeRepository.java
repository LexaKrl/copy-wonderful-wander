package com.technokratos.repository.custom;

import com.mongodb.client.result.DeleteResult;
import com.technokratos.model.LikeEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CustomLikeRepository {

    private final MongoTemplate mongoTemplate;

    public boolean likeExists(String userId, String postId) {
        Query query = new Query(
                Criteria.where("user.userId").is(userId)
                        .and("postId").is(postId)
        );
        return mongoTemplate.exists(query, LikeEntity.class);
    }

    public DeleteResult removeByUserIdAndPostId(String userId, String postId) {
        Query query = new Query(
                Criteria.where("user.userId").is(userId)
                        .and("postId").is(postId)
        );

        return mongoTemplate.remove(query, LikeEntity.class);
    }

}
