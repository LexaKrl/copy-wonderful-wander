package com.technokratos.repository.custom;

import com.mongodb.client.result.DeleteResult;
import com.technokratos.model.CommentEntity;
import com.technokratos.model.LikeEntity;
import com.technokratos.model.PostEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CustomCommentRepository {

    private final MongoTemplate mongoTemplate;

    public void incrementRepliesCount(String commentId) {
        mongoTemplate.updateFirst(
                Query.query(Criteria.where("commentId").is(commentId)),
                new Update().inc("repliesCount", 1),
                CommentEntity.class
        );
    }

    public void decrementRepliesCount(String commentId) {
        mongoTemplate.updateFirst(
                Query.query(Criteria.where("commentId").is(commentId)),
                new Update().inc("repliesCount", -1),
                CommentEntity.class
        );
    }

    public DeleteResult removeByCommentId(String commentId) {
        Query query = new Query(
                new Criteria().orOperator(
                        Criteria.where("commentId").is(commentId),
                        Criteria.where("rootCommentId").is(commentId)
                )
        );

        return mongoTemplate.remove(query, CommentEntity.class);
    }

}
