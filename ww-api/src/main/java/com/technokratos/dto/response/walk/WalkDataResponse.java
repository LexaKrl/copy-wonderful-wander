package com.technokratos.dto.response.walk;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record WalkDataResponse(
        @Schema(description = "Longitude", example = "-77.0364")
        Double longitude,

        @Schema(description = "Latitude", example = "38.8951")
        Double latitude,

        @Schema(description = "information about steps passed since last timestamp", example = "180")
        Integer steps,

        @Schema(description = "Information about meters passed since last timestamp", example = "213")
        Integer meters,

        @Schema(
                description = "UUIDs of photos uploaded by walk owner during the walk. List may be empty if user hasn't uploaded photo since the last WalkDataRequest",
                example = """
                        "a1e5f6d4-e2f3-4a8b-eb5c-1e2f3a4b5c6d",
                        "ad4d3a54-e5f6-47ab-9c0d-1b5c3a4b3a4d",
                        "a1bb5cd4-e5f6-4c8b-9c0d-13a43a4b5c6d"
                        """
        )
        List<UUID> uuidsPhotos,

        @Schema(description = "Time when data were posted", example = "2023-10-05 14:30:00")
        LocalDateTime time
) {
}
