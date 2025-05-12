package com.technokratos.dto.request.user;

public record UserProfileUpdateRequest(
        String firstname,
        String lastname,
        String username,
        String email,
        String bio,
        String avatarUrl) {
}
