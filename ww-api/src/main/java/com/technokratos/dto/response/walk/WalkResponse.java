package com.technokratos.dto.response.walk;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record WalkResponse(

        @Schema(description = "Unique walk ID", example = "550e8400-e29b-41d4-a716-446655440000")
        UUID walkId,

        @Schema(description = "Unique user ID", example = "550e8400-e29b-41d4-a716-446655440000")
        UUID ownerId,

        @Schema(description = "Walk name", example = "Wonderful wander")
        String name,

        @Size(max = 255)
        @Schema(description = "Walk description", example = "Daily morning walk in the park")
        String description,

        @Schema(
                description = "UUID list of participants of the walk",
                example = """
                        [
                            "a1e5f6d4-e2f3-4a8b-eb5c-1e2f3a4b5c6d",
                            "ad4d3a54-e5f6-47ab-9c0d-1b5c3a4b3a4d",
                            "a1bb5cd4-e5f6-4c8b-9c0d-13a43a4b5c6d",
                            "a1e5f6d4-f923-47ab-9c0d-1e2f3a4b5c6d",
                            "b5c6d7e8-f9d0-41h2-3j4k-5l6m7n8o9p0q"
                        ]
                        """
        )
        List<UUID> walkParticipants,

        @Schema(
                description = "UUID list of photos of the walk",
                example = """
                        [
                            "a1e5f6d4-e2f3-4a8b-eb5c-1e2f3a4b5c6d",
                            "ad4d3a54-e5f6-47ab-9c0d-1b5c3a4b3a4d",
                            "a1bb5cd4-e5f6-4c8b-9c0d-13a43a4b5c6d",
                            "a1e5f6d4-f923-47ab-9c0d-1e2f3a4b5c6d",
                            "b5c6d7e8-f9d0-41h2-3j4k-5l6m7n8o9p0q"
                        ]
                        """
        )
        List<UUID> walkPhotos,

        @Schema(description = "Time walk was created", example = "2023-10-05 14:30:00")
        LocalDateTime createdAt
) {
}
