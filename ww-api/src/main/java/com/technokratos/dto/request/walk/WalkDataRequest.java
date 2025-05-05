package com.technokratos.dto.request.walk;

import io.swagger.v3.oas.annotations.media.Schema;

public record WalkDataRequest(

        @Schema(description = "Longitude", example = "-77.0364")
        Double longitude,

        @Schema(description = "Latitude", example = "38.8951")
        Double latitude,

        @Schema(description = "information about steps passed since last timestamp", example = "180")
        Integer steps,

        @Schema(description = "Information about meters passed since last timestamp", example = "213")
        Integer meters
) {
}
