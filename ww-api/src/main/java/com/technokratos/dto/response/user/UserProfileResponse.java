package com.technokratos.dto.response.user;

import java.util.UUID;

public record UserProfileResponse(
        UUID userId,
        String username,
        String firstname,
        String lastname,
        String bio,
        String avatarUrl,
        int followersCount,
        int followingCount,
        int friendsCount) {
}
