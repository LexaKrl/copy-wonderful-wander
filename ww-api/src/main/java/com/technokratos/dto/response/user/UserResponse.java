package com.technokratos.dto.response.user;

import com.technokratos.enums.security.UserRole;
import com.technokratos.enums.user.PhotoVisibility;
import com.technokratos.enums.user.WalkVisibility;

import java.util.UUID;

public record UserResponse(
        UUID userId,
        String username,
        String email,
        String firstname,
        String lastname,
        String bio,
        UserRole role,
        String avatarUrl,
        int followersCount,
        int followingCount,
        int friendsCount,
        PhotoVisibility photoVisibility,
        WalkVisibility walkVisibility) {
}
