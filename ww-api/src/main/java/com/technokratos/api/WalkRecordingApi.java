package com.technokratos.api;

import com.technokratos.dto.request.walk.WalkDataRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.UUID;

@Tag(name = "Walk", description = "The Walk API")
@RequestMapping("/api/walks")
public interface WalkRecordingApi {

    /*
    *   Subscribe on streaming
    * */

    @Operation(
            summary = "Subscribe on streaming location data",
            description = "Subscribe to receive data from server",
            tags = {"Walk recording"},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully subscribed"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Walk not found"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid data"
                    )
            }
    )
    @GetMapping("/{walkId}/stream/subscribe")
    SseEmitter subscribe(
            @Parameter(
                    description = "UUID of the walk",
                    example = "550e8400-e29b-41d4-a716-446655440000",
                    required = true
            )
            @PathVariable String walkId
    );

    /*
    *   Stream data
    * */

    @Operation(
            summary = "Streaming data when it is received from main user",
            description = "Stream data to all subscribers",
            tags = {"Walk recording"}
    )
    @PostMapping("/{walkId}/stream/data")
    ResponseEntity<Void> streamData(
            @Parameter(
                    description = "UUID of the walk",
                    example = "550e8400-e29b-41d4-a716-446655440000",
                    required = true
            )
            @PathVariable UUID walkId,
            @Parameter(
                    description = "Walk data to stream to subscribers",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = WalkDataRequest.class)
                    )
            )
            @RequestBody @Valid WalkDataRequest walkDataRequest
    );
}
