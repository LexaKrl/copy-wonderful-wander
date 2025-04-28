package com.technokratos.dto.request.user;

public record UserProfileUpdateRequest(
        String name,
        String lastname,
        String username,
        String email,
        String bio,
        String avatarUrl) {
}
