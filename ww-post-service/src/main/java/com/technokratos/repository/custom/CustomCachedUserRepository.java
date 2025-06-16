package com.technokratos.repository.custom;

import com.technokratos.client.UserClient;
import com.technokratos.model.CachedUserEntity;
import com.technokratos.model.UserFriendEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class CustomCachedUserRepository {

    private final MongoTemplate mongoTemplate;
    private final UserClient userClient;

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

    public void syncFriendsForUser(String userId) {
        List<String> friendIds = userClient.getFriendsByUserId(UUID.fromString(userId))
                .stream()
                .map(String::valueOf)
                .toList();

        Query deleteQuery = Query.query(Criteria.where("userId").is(userId));
        mongoTemplate.remove(deleteQuery, UserFriendEntity.class);

        List<UserFriendEntity> friendsToSave = friendIds.stream()
                .map(friendId -> UserFriendEntity.builder()
                        .id(String.valueOf(UUID.randomUUID()))
                        .userId(userId)
                        .friendId(friendId)
                        .build())
                .toList();

        mongoTemplate.insertAll(friendsToSave);
    }

    public <T> void updateEmbeddedUserInCollection(String userId, String newAvatarFilename, Class<T> entityClass) {
        Query query = Query.query(Criteria.where("user.userId").is(userId));
        Update update = Update.update("user.avatarFilename", newAvatarFilename);
        mongoTemplate.updateMulti(query, update, entityClass);
    }


}
