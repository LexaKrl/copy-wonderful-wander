package com.technokratos.repository.custom;

import com.technokratos.model.CachedUserEntity;
import com.technokratos.model.UserFriendEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

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

//    public List<UserFriendEntity> getUserFriend(String userId) {
//        Query query = new Query(Criteria.where("userOneId").is(userId));
//        query.fields().include("userTwoId");
//
//        return mongoTemplate.find(query, UserFriendEntity.class);
//    }

    public boolean isFriends(String userId1, String userId2) {
        return mongoTemplate.exists(
                Query.query(Criteria.where("userId").is(userId1).and("friendId").is(userId2)),
                UserFriendEntity.class);
    }

    public void deleteFriend(String userId, String friendId) {
        Query query = Query.query(Criteria.where("userId").is(userId)
                .and("friendId").is(friendId));

        mongoTemplate.remove(query, UserFriendEntity.class);
    }

    public <T> void updateEmbeddedUserInCollection(String userId, String newAvatarFilename, Class<T> entityClass) {
        Query query = Query.query(Criteria.where("user.userId").is(userId));
        Update update = Update.update("user.avatarFilename", newAvatarFilename);
        mongoTemplate.updateMulti(query, update, entityClass);
    }
}
