package com.technokratos.api;

import com.technokratos.dto.request.walk.WalkDataRequest;
import com.technokratos.dto.request.walk.WalkRequest;
import com.technokratos.dto.response.walk.WalkResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "Walk", description = "The Walk API")
@RequestMapping("/api/walks")
public interface WalkApi {

    /*
     *   Get walks
     *
     * */

    @Operation(
            summary = "Get all walks",
            description = "Get sorted page of walks",
            tags = {"walks"},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Walks received",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = WalkResponse.class))
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Walks not found"
                    )
            }
    )
    @GetMapping
    ResponseEntity<Page<WalkResponse>> getWalks(
            @Parameter(
                    description = "Pageable type with size and sort type",
                    required = true
            )
            @PageableDefault(size = 20, sort = "createdAt")
            Pageable pageable
    );

    /*
     *   Create walk
     *
     * */

    @Operation(
            summary = "Create a walk",
            description = "Create a walk",
            tags = {"walks"},
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Walk created"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Walk creation failed"
                    )
            }
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    void createWalk(
            @Parameter(
                    description = "Data for creation walk",
                    required = true,
                    content = @Content(schema = @Schema(implementation = WalkRequest.class))
            )
            @RequestBody @Valid
            WalkRequest walkRequest
    );

    /*
     *   Get walk
     * */

    @Operation(
            summary = "Get a walk by its ID",
            description = "There is a walk_id in the path. So we are getting a walk by its ID",
            tags = {"walks"},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Walk retrieved",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = WalkResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Walk not found"
                    )
            }
    )
    @GetMapping("/{walkId}")
    ResponseEntity<WalkResponse> getWalk(
            @Parameter(
                    description = "UUID of the walk",
                    example = "550e8400-e29b-41d4-a716-446655440000",
                    required = true
            )
            @PathVariable UUID walkId
    );

    /*
     *   Delete walk
     * */

    @Operation(
            summary = "Delete a walk by its ID",
            description = "There is a path variable walk_id. So we take this and delete walk by its ID",
            tags = {"walks"},
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Walk successfully deleted"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Walk not found"
                    )
            }
    )
    @DeleteMapping("/{walkId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteWalk(
            @Parameter(
                    description = "UUID of the walk to delete",
                    example = "550e8400-e29b-41d4-a716-446655440000",
                    required = true
            )
            @PathVariable UUID walkId
    );

    /*
     *   Update walk
     * */

    @Operation(
            summary = "Update a walk by its ID",
            description = "There is a path variable walk_id. So we take this and update walk by its ID",
            tags = {"walks"},
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Walk successfully updated"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid input data"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Walk not found"
                    )
            }
    )
    @PutMapping("/{walkId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    ResponseEntity<Void> updateWalk(
            @Parameter(
                    description = "The body of the walk request that user sends to the server",
                    required = true,
                    content = @Content(schema = @Schema(implementation = WalkRequest.class))
            )
            @RequestBody @Valid WalkRequest walkRequest,
            @Parameter(
                    description = "UUID of the walk to update",
                    example = "550e8400-e29b-41d4-a716-446655440000",
                    required = true
            )
            @PathVariable UUID walkId
    );


    /*
     *   Record data using method
     *
     * */

    /*@Operation(
            summary = "Recording location data",
            description = "Record location data from the user to the server",
            tags = {"Walk recording"},
            responses = {
                    @ApiResponse(
                            responseCode = "202",
                            description = "Location updated"
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
    @PostMapping("/{walkId}/record")
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
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = WalkDataRequest.class))
                    )
            )
            @RequestBody List<WalkDataRequest> walkDataRequests
    );*/
}
