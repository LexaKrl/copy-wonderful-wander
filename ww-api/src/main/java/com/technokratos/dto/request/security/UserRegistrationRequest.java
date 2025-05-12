package com.technokratos.dto.request.security;

import java.util.UUID;

public record UserRegistrationRequest(
        UUID userId,
        String username,
        String password,
        String email,
        String firstname,
        String lastname) {
}
