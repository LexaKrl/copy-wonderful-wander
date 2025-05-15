package com.technokratos.dto.request.security;

import com.technokratos.enums.security.UserRole;

import java.util.UUID;

public record UserForJwtTokenRequest(
        UUID userId,
        String username,
        UserRole role) {
}
