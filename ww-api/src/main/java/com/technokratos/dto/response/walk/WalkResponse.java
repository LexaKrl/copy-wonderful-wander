package com.technokratos.dto.response.walk;

import com.technokratos.enums.walk.WalkStatus;
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

        @Schema(description = "All steps made during the walk")
        Integer totalSteps,

        @Schema(description = "All meters covered during the walk")
        Integer totalMeters,

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
                description = "Links of photos uploaded by user",
                example = """
                        "http://example.com/123",
                        "http://example.com/456",
                        "http://example.com/789"
                        """
        )
        List<String> photos,

        @Schema(description = "Time walk was created", example = "2023-10-05 14:30:00")
        LocalDateTime createdAt,

        @Schema(description = "Longitude", example = "-77.0364")
        Double startPointLongitude,

        @Schema(description = "Latitude", example = "38.8951")
        Double startPointLatitude,

        @Schema(description = "LineString parsed to String in geoJson format")
        String geoJsonRoute,

        @Schema(description = "Walk status")
        WalkStatus walkStatus
) {
}
