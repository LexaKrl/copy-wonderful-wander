package com.technokratos.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class ApiEndpoint {
    public static final class UserController {
        public static final String GET_CURRENT_USER_PROFILE = "/api/users/me";
        public static final String UPDATE_CURRENT_USER = "/api/users/me";
        public static final String DELETE_CURRENT_USER = "/api/users/me";
    }
}