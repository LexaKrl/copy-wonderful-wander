package com.technokratos.dto.request;

import java.util.UUID;

public record WalkRequest(
        UUID id,
        UUID userId
) {
}
