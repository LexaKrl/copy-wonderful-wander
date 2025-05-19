package com.technokratos.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class ApiEndpoint {
    public static final class UserController {
        public static final String GET_CURRENT_USER_PROFILE = "/api/users/me";
        public static final String UPDATE_CURRENT_USER = "/api/users/me";
        public static final String DELETE_CURRENT_USER = "/api/users/me";
        public static final String FOLLOW = "/api/users/me/follows/{targetUserId}";
        public static final String UNFOLLOW = "/api/users/me/follows/{targetUserId}";
        public static final String GET_USER_PROFILE_BY_ID = "/api/users/{targetUserId}";
        public static final String GET_FRIENDS_BY_USER_ID = "/api/users/{userId}/friends";
        public static final String GET_FOLLOWING_BY_USER_ID = "/api/users/{userId}/following";
        public static final String GET_FOLLOWERS_BY_USER_ID = "/api/users/{userId}/followers";
    }
}