package com.technokratos.dto.response;

import java.util.UUID;

public record UserCompactResponse(UUID userId, String username, String avatarUrl) {
}
