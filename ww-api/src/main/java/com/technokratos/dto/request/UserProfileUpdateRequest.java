package com.technokratos.dto.request;

public record UserProfileUpdateRequest(
        String name,
        String lastname,
        String username,
        String email,
        String bio,
        String avatarUrl) {
}
