package com.technokratos.dto.request;

import java.util.UUID;

public record UserForJwtTokenRequest(
        UUID id,
        String username) {
}
