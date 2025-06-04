package com.technokratos.api;

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
    Page<WalkResponse> getWalks(
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
                            responseCode = "404",
                            description = "Walk not found"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid data"
                    )
            }
    )
    @GetMapping("/{walkId}")
    WalkResponse getWalk(
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
    void updateWalk(
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
    *   Add walk participant
    * */

    @Operation(
            summary = "Add walk participant to the walk",
            description = "Add new participant to the walk using his Id",
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
    @PostMapping("/{walkId}/participant")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void addParticipant(
            @Parameter(
                    description = "UUID of the participant user want to add",
                    example = "550e8400-e29b-41d4-a716-446655440000",
                    required = true
            )
            @RequestParam UUID participantsId,
            @Parameter(
                    description = "UUID of the walk to update",
                    example = "550e8400-e29b-41d4-a716-446655440000",
                    required = true
            )
            @PathVariable UUID walkId
    );

    /*
    *   Remove participant of the walk
    * */

    @Operation(
            summary = "Add walk participant to the walk",
            description = "Add new participant to the walk using his Id",
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
    @DeleteMapping("/{walkId}/participant")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void removeParticipant(
            @Parameter(
                    description = "UUID of the participant user want to remove",
                    example = "550e8400-e29b-41d4-a716-446655440000",
                    required = true
            )
            @RequestParam UUID participantsId,
            @Parameter(
                    description = "UUID of the walk to update",
                    example = "550e8400-e29b-41d4-a716-446655440000",
                    required = true
            )
            @PathVariable UUID walkId
    );
}
