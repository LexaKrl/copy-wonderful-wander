package com.technokratos.dto.request.security;

import com.technokratos.enums.security.UserRole;

public record AdminUserUpdateRequest(
        String username,
        String email,
        UserRole role) {
}
