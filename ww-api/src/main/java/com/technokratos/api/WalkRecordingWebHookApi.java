package com.technokratos.api;

import com.technokratos.dto.request.LocationData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.web.bind.annotation.*;

@RequestMapping("api/webhook/walks")
public interface WalkRecordingWebHookApi {

    @Operation(
            summary = "Recording location data",
            description = "Record location data from the user to the server",
            tags = {"Walk recording"}
    )
    @ApiResponse(
        responseCode = "202",
        description = "Location updated"
    )
    @ApiResponse(
        responseCode = "404",
        description = "Walk not found"
    )
    @ApiResponse(
        responseCode = "400",
        description = "Invalid data"
    )
    @PostMapping("/{walkId}")
    void recordData(
            @Parameter(
                    description = "UUID of the walk which data is recording",
                    example = "550e8400-e29b-41d4-a716-446655440000",
                    required = true
            )
            @PathVariable String walkId,
            @Parameter(
                    description = "The location data",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = LocationData.class)
                    )
            )
            @RequestBody LocationData location
    );
}
