package com.technokratos.dto.request.walk;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.util.List;
import java.util.UUID;

public record WalkRequest(

        @Schema(description = "Walk name", example = "Wonderful wander")
        @Size(min = 3, max = 100, message = "Walk name must be between 3 and 100 characters")
        @Pattern(regexp = "^[a-zA-Z0-9\\s\\-.,'()]+$", message = "Walk name contains invalid characters")
        String name,

        @Schema(description = "Walk description", example = "Daily morning walk in the park")
        @Size(max = 255)
        @Pattern(regexp = "^[a-zA-Z0-9\\s\\-.,'()!?]*$", message = "Description contains invalid characters")
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
        @NotNull(message = "Participants list cannot be null")
        @Size(min = 0, max = 20, message = "There must be between 0 and 20 participants")
        List<
                @NotBlank(message = "Participant UUID cannot be blank")
                @Pattern(regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$",
                        message = "Invalid UUID format")
                UUID
        > walkParticipants,

        @Schema(description = "Longitude", example = "-77.0364")
        @DecimalMin(value = "-180.0", message = "Longitude must be >= -180")
        @DecimalMax(value = "180.0", message = "Longitude must be <= 180")
        Double startPointLongitude,

        @Schema(description = "Latitude", example = "38.8951")
        @DecimalMin(value = "-90.0", message = "Latitude must be >= -90")
        @DecimalMax(value = "90.0", message = "Latitude must be <= 90")
        Double startPointLatitude
) {
}
