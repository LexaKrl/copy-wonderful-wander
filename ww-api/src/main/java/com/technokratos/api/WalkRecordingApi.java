package com.technokratos.api;

import com.technokratos.dto.request.walk.WalkDataRequest;
import com.technokratos.util.HttpHeaders;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
                    description = "UUID of the currentUser",
                    example = "550e8400-e29b-41d4-a716-446655440000",
                    required = true
            )
            @Schema(hidden = true) @RequestHeader(HttpHeaders.USER_ID) UUID currentUserId,
            @Parameter(
                    description = "UUID of the walk",
                    example = "550e8400-e29b-41d4-a716-446655440000",
                    required = true
            )
            @PathVariable UUID walkId
    );

    /*
     *   Finish walk
     * */

    @Operation(
            summary = "Finish streaming location data",
            description = "Finish walk and stop sending data to the server",
            tags = {"Walk recording"},
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Successfully finished"
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
    @PostMapping("/{walkId}/stream/finish")
    void finishWalk(
            @Parameter(
                    description = "UUID of the currentUser",
                    example = "550e8400-e29b-41d4-a716-446655440000",
                    required = true
            )
            @Schema(hidden = true) @RequestHeader(HttpHeaders.USER_ID) UUID currentUserId,
            @Parameter(
                    description = "UUID of the walk",
                    example = "550e8400-e29b-41d4-a716-446655440000",
                    required = true
            )
            @PathVariable UUID walkId
    );

    /*
    *   Stream data
    * */

    @Operation(
            summary = "Streaming data when it is received from main user",
            description = "Stream data to all subscribers",
            tags = {"Walk recording"},
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Successfully sent"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid data"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Walk not found"
                    )
            }
    )
    @PostMapping("/{walkId}/stream/data")
    void streamData(
            @Parameter(
                    description = "UUID of the currentUser",
                    example = "550e8400-e29b-41d4-a716-446655440000",
                    required = true
            )
            @Schema(hidden = true) @RequestHeader(HttpHeaders.USER_ID) UUID currentUserId,
            @Parameter(
                    description = "UUID of the walk",
                    example = "550e8400-e29b-41d4-a716-446655440000",
                    required = true
            )
            @PathVariable @Valid UUID walkId,
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
