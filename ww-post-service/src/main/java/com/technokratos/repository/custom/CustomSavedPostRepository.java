package com.technokratos.repository.custom;

import com.technokratos.model.SavedPostEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class CustomSavedPostRepository {

    private final MongoTemplate mongoTemplate;

    public List<String> getSavedPostIdsByUserId(String userId) {
        Query query = new Query(Criteria.where("userId").is(userId));
        query.fields().include("postId");
        return mongoTemplate.find(query, SavedPostEntity.class)
                .stream()
                .map(SavedPostEntity::getPostId).collect(Collectors.toList());
    }
}
