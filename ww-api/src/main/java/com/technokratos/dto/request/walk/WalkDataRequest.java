package com.technokratos.dto.request.walk;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;
import java.util.List;

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

        @Schema(
                description = "String UUID.jpeg(example) of photos uploaded by user. When client uploads a photo he receives a photo UUID",
                example = """
                        "a1e5f6d4-e2f3-4a8b-eb5c-1e2f3a4b5c6d.jpeg",
                        "ad4d3a54-e5f6-47ab-9c0d-1b5c3a4b3a4d.jpeg",
                        "a1bb5cd4-e5f6-4c8b-9c0d-13a43a4b5c6d.jpeg"
                        """
        )
        @NotNull(message = "Photo String list cannot be null")
        @Size(max = 10, message = "Maximum 10 photos per request allowed")
        List<
                @NotBlank(message = "Participant UUID cannot be blank")
                @Pattern(regexp = "^(?i)[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}\\.(jpe?g|png|gif|bmp|webp)$",
                        message = "Invalid String UUID.ext format")
                String
        > photos,

        @Schema(description = "Time when data were posted", example = "2023-10-05 14:30:00")
        @NotNull(message = "Timestamp cannot be null")
        @PastOrPresent(message = "Timestamp must be in past or present")
        LocalDateTime time
) {
}
