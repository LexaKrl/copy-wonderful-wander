package com.technokratos.dto.request.walk;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

public record LocationData(
        @Schema(description = "Unique ID", example = "550e8400-e29b-41d4-a716-446655440000")
        UUID walkId,

        @Schema(description = "Longitude", example = "-77.0364")
        Double longitude,

        @Schema(description = "Latitude", example = "38.8951")
        Double latitude
) {
}
