package com.technokratos.dto.request.security;

import java.util.UUID;

public record UserForJwtTokenRequest(
        UUID id,
        String username) {
}
