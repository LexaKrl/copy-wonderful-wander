package com.technokratos.dto.response.user;

import com.technokratos.enums.user.PhotoVisibility;
import com.technokratos.enums.user.WalkVisibility;

import java.util.UUID;

public record UserResponse(
        UUID userId,
        String username,
        String firstname,
        String lastname,
        String bio,
        String email,
        String avatarUrl,
        int followersCount,
        int followingCount,
        int friendsCount,
        PhotoVisibility photoVisibility,
        WalkVisibility walkVisibility) {
}
