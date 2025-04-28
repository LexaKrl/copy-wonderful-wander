package com.technokratos.dto.request;

import com.technokratos.enums.UserRole;

public record AdminUserUpdateRequest(
        String username,
        String email,
        UserRole role) {
}
