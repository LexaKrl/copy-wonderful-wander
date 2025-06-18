package com.technokratos.model;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "user_friends")
@Getter
@Builder
public class UserFriendEntity {
    @Id
    private String id;
    @Indexed
    private String userId;
    @Indexed
    private String friendId;
}
