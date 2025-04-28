package com.technokratos.dto.response.user;

import java.util.UUID;

public record UserCompactResponse(
        UUID userId,
        String username,
        String avatarUrl) {
}
