package com.technokratos.dto.response;

import java.util.UUID;

public record UserResponse(
        UUID id,
        String username,
        String email,
        String name,
        String lastname,
        String bio) {
}
