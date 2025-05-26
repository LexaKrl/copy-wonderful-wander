package com.technokratos.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class PathVariable {
    public static final class UserController {
        public static final String USER_ID = "userId";
        public static final String TARGET_USER_ID = "targetUserId";
    }
}