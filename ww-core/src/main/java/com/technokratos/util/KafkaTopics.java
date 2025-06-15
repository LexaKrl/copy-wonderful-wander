package com.technokratos.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class KafkaTopics {
    public static final String USER_AVATAR_SAVED_TOPIC = "user-avatar-saved";
    public static final String PHOTO_OF_WALK_SAVED_TOPIC = "photo-walk-saved";
    public static final String USER_CREATED_TOPIC = "user-created";
    public static final String USER_UPDATED_TOPIC = "user-updated";
    public static final String USER_DELETED_TOPIC = "user-deleted";
    public static final String USER_FRIENDSHIP_UPDATE = "user-friendship-update";
}
