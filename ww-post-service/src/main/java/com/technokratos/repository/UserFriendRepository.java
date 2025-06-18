package com.technokratos.repository;

import com.technokratos.model.SavedPostEntity;
import com.technokratos.model.UserFriendEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserFriendRepository extends MongoRepository<UserFriendEntity, String> {
}
