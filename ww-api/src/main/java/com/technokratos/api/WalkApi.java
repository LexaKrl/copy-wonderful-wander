package com.technokratos.api;

import com.technokratos.dto.request.walk.WalkRequest;
import com.technokratos.dto.response.walk.WalkResponse;
import com.technokratos.util.HttpHeaders;
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
                    description = "UUID of the currentUser",
                    example = "550e8400-e29b-41d4-a716-446655440000",
                    required = true
            )
            @Schema(hidden = true) @RequestHeader(HttpHeaders.USER_ID) UUID requesterId,
            @Parameter(
                    description = "Pageable type with size and sort type",
                    required = true
            )
            @PageableDefault(size = 20, sort = "createdAt")
            Pageable pageable
    );

    /*
    *   Get all user's walks
    * */

    @Operation(
            summary = "Get all walks for defined user",
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
    @GetMapping("/user/{userId}")
    Page<WalkResponse> getWalksForUser(
            @Parameter(
                    description = "UUID of the user",
                    example = "550e8400-e29b-41d4-a716-446655440000",
                    required = true
            )
            @PathVariable
            UUID userId,
            @Parameter(
                    description = "UUID of the currentUser",
                    example = "550e8400-e29b-41d4-a716-446655440000",
                    required = true
            )
            @Schema(hidden = true) @RequestHeader(HttpHeaders.USER_ID) UUID requesterId,
            @Parameter(
                    description = "Pageable type with size and sort type",
                    required = true
            )
            @PageableDefault(size = 20, sort = "createdAt")
            Pageable pageable
    );

    /*
    *   Get all walks where user is participant
    * */

    @Operation(
            summary = "Get all walks where user participant",
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
    @GetMapping("/participant")
    Page<WalkResponse> getWalksWhereUserParticipant(
            @Parameter(
                    description = "UUID of the currentUser",
                    example = "550e8400-e29b-41d4-a716-446655440000",
                    required = true
            )
            @Schema(hidden = true) @RequestHeader(HttpHeaders.USER_ID) UUID requesterId,
            @Parameter(
                    description = "Pageable type with size and sort type",
                    required = true
            )
            @PageableDefault(size = 20, sort = "createdAt")
            Pageable pageable
    );

    /*
     *   Get all walks of followed users user subscribed on
     * */

    @Operation(
            summary = "Get all walks of followed users user subscribed on",
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
    @GetMapping("/subscribed")
    Page<WalkResponse> getWalksUserSubscribedOn(
            @Parameter(
                    description = "UUID of the currentUser",
                    example = "550e8400-e29b-41d4-a716-446655440000",
                    required = true
            )
            @Schema(hidden = true) @RequestHeader(HttpHeaders.USER_ID) UUID requesterId,
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
                    description = "UUID of the currentUser",
                    example = "550e8400-e29b-41d4-a716-446655440000",
                    required = true
            )
            @Schema(hidden = true) @RequestHeader(HttpHeaders.USER_ID) UUID currentUserId,
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
                    description = "UUID of the currentUser",
                    example = "550e8400-e29b-41d4-a716-446655440000",
                    required = true
            )
            @Schema(hidden = true) @RequestHeader(HttpHeaders.USER_ID) UUID requesterId,
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
                    description = "UUID of the currentUser",
                    example = "550e8400-e29b-41d4-a716-446655440000",
                    required = true
            )
            @Schema(hidden = true) @RequestHeader(HttpHeaders.USER_ID) UUID currentUserId,
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
                    description = "UUID of the currentUser",
                    example = "550e8400-e29b-41d4-a716-446655440000",
                    required = true
            )
            @Schema(hidden = true) @RequestHeader(HttpHeaders.USER_ID) UUID currentUserId,
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
                    description = "UUID of the currentUser",
                    example = "550e8400-e29b-41d4-a716-446655440000",
                    required = true
            )
            @Schema(hidden = true) @RequestHeader(HttpHeaders.USER_ID) UUID currentUserId,
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
                    description = "UUID of the currentUser",
                    example = "550e8400-e29b-41d4-a716-446655440000",
                    required = true
            )
            @Schema(hidden = true) @RequestHeader(HttpHeaders.USER_ID) UUID currentUserId,
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

    /*
     *   Accept walk invite
     *
     * */

    @Operation(
            summary = "Accept invite to walk",
            description = "Accept invite to walk",
            tags = {"walks"},
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Invite accepted"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Acceptation failed"
                    )
            }
    )
    @GetMapping("/{walkId}/participant/{participantId}/accept")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void acceptInvite(
            @Parameter(
                    description = "UUID acceptationToken",
                    example = "550e8400-e29b-41d4-a716-446655440000",
                    required = true
            )
            UUID acceptationToken,
            @Parameter(
                    description = "UUID of the walk",
                    example = "550e8400-e29b-41d4-a716-446655440000",
                    required = true
            )
            @PathVariable UUID walkId,
            @Parameter(
                    description = "UUID of the participant",
                    example = "550e8400-e29b-41d4-a716-446655440000",
                    required = true
            )
            @PathVariable UUID participantId);
}
