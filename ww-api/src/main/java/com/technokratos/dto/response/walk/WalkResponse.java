package com.technokratos.dto.response.walk;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record WalkResponse(
        @Schema(description = "Unique walk ID", example = "550e8400-e29b-41d4-a716-446655440000")
        UUID id,

        @Schema(description = "Unique user ID", example = "550e8400-e29b-41d4-a716-446655440000")
        UUID userId,

        @Schema(description = "Walk name", example = "Wonderful wander")
        String name,

        @Size(max = 255)
        @Schema(description = "Walk description", example = "Daily morning walk in the park")
        String description
) {
}
