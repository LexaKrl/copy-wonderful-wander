package com.technokratos.dto.response.walk;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;

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
                description = "Links of photos uploaded by user",
                example = """
                        "http://example.com/123",
                        "http://example.com/456",
                        "http://example.com/789"
                        """
        )
        List<String> photos,

        @Schema(description = "Time when data were posted", example = "2023-10-05 14:30:00")
        LocalDateTime time
) {
}
