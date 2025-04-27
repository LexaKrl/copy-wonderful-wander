package com.technokratos.dto.response;

import java.util.UUID;

public record UserProfileResponse(UUID userId, String username, String bio, String avatarUrl,
                                  int followersCount, int followingCount, int friendsCount) {
}
