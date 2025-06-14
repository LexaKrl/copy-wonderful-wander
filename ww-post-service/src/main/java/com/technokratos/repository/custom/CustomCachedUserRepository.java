package com.technokratos.repository.custom;

import com.technokratos.model.CachedUserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CustomCachedUserRepository {

    private final MongoTemplate mongoTemplate;

    public CachedUserEntity getMyPhotoVisibility(String userId) {
        Query query = new Query(Criteria.where("userId").is(userId));
        query.fields().include("myPhotoVisibility");

        return mongoTemplate.findOne(query, CachedUserEntity.class);
    }

    public CachedUserEntity getSavedPhotoVisibility(String userId) {
        Query query = new Query(Criteria.where("userId").is(userId));
        query.fields().include("savedPhotoVisibility");

        return mongoTemplate.findOne(query, CachedUserEntity.class);
    }

    public CachedUserEntity getUserFriend(String userId) {
        Query query = new Query(Criteria.where("userId").is(userId));
        query.fields().include("friends");

        return mongoTemplate.findOne(query, CachedUserEntity.class);

    }

}
