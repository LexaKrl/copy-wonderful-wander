package com.technokratos.dto.request.walk;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

public record WalkDataRequest(

        @Schema(description = "Longitude", example = "-77.0364")
        @NotNull(message = "Longitude cannot be null")
        @DecimalMin(value = "-180.0", message = "Longitude must be >= -180")
        @DecimalMax(value = "180.0", message = "Longitude must be <= 180")
        Double longitude,

        @Schema(description = "Latitude", example = "38.8951")
        @NotNull(message = "Latitude cannot be null")
        @DecimalMin(value = "-90.0", message = "Latitude must be >= -90")
        @DecimalMax(value = "90.0", message = "Latitude must be <= 90")
        Double latitude,

        @Schema(description = "information about steps passed since last timestamp", example = "180")
        @NotNull(message = "Steps cannot be null")
        @PositiveOrZero(message = "Steps must be positive or zero")
        @Max(value = 10000, message = "Steps value is unrealistic (max 10000)")
        Integer steps,

        @Schema(description = "Information about meters passed since last timestamp", example = "213")
        @NotNull(message = "Meters cannot be null")
        @PositiveOrZero(message = "Meters must be positive or zero")
        @Max(value = 10000, message = "Meters value is unrealistic (max 10000)")
        Integer meters,

        @Schema(description = "Time when data were posted", example = "2023-10-05 14:30:00")
        @NotNull(message = "Timestamp cannot be null")
        @PastOrPresent(message = "Timestamp must be in past or present")
        LocalDateTime time
) {
}
