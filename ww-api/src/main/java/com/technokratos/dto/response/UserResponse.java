package com.technokratos.dto.response;

import com.technokratos.enums.PhotoVisibility;
import com.technokratos.enums.WalkVisibility;

import java.util.UUID;

public record UserResponse(UUID userId, String username, String bio, String email, String avatarUrl,
                           int followersCount, int followingCount, int friendsCount, PhotoVisibility photoVisibility,
                           WalkVisibility walkVisibility) {
}
