package com.technokratos.api;

import com.technokratos.dto.exception.BaseExceptionMessage;
import com.technokratos.dto.exception.ValidationExceptionMessage;
import com.technokratos.dto.response.post.LikeResponse;
import com.technokratos.dto.response.post.PostResponse;
import com.technokratos.dto.response.user.UserCompactResponse;
import com.technokratos.util.HttpHeaders;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(
        name = "Лайки",
        description = "Основные методы для взаимодействия с лайками пользователей"
)
@Validated
@RequestMapping(value = "/api/posts/{postId}/likes", produces = MediaType.APPLICATION_JSON_VALUE)
public interface LikeApi {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Получить список лайков поста", description = "Возвращает список пользователей, поставивших лайк указанному посту")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Лайки поста успешно получены",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UserCompactResponse.class))),
            @ApiResponse(responseCode = "400", description = "Невалидный uuid поста",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ValidationExceptionMessage.class))),
            @ApiResponse(responseCode = "401", description = "Пользователь не аутентифицирован",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = BaseExceptionMessage.class))),
            @ApiResponse(responseCode = "404", description = "Пост не найден",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = BaseExceptionMessage.class)))
    })
    List<UserCompactResponse> getLikesByPostId(
            @Schema(hidden = true) @RequestHeader(HttpHeaders.USER_ID) String currentUserId,
            @Parameter(description = "ID поста", example = "550e8400-e29b-41d4-a716-446655440000")
            @PathVariable String postId,
            Pageable pageable);

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Поставить лайк посту", description = "Добавляет лайк текущего пользователя к указанному посту")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Лайк успешно поставлен",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = LikeResponse.class))),
            @ApiResponse(responseCode = "400", description = "Невалидный uuid поста",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ValidationExceptionMessage.class))),
            @ApiResponse(responseCode = "401", description = "Пользователь не аутентифицирован",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = BaseExceptionMessage.class))),
            @ApiResponse(responseCode = "404", description = "Пост не найден",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = BaseExceptionMessage.class))),
            @ApiResponse(responseCode = "409", description = "Лайк уже поставлен",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = BaseExceptionMessage.class)))
    })
    LikeResponse createLike(
            @Schema(hidden = true) @RequestHeader(HttpHeaders.USER_ID) String currentUserId,
            @Parameter(description = "ID поста", example = "550e8400-e29b-41d4-a716-446655440000")
            @PathVariable String postId);

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Убрать лайк с поста", description = "Удаляет лайк текущего пользователя с указанного поста")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Лайк успешно удален",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = LikeResponse.class))),
            @ApiResponse(responseCode = "401", description = "Пользователь не аутентифицирован",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = BaseExceptionMessage.class))),
            @ApiResponse(responseCode = "404", description = "Пост не найден",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = BaseExceptionMessage.class))),
            @ApiResponse(responseCode = "409", description = "Лайк уже убран",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = BaseExceptionMessage.class)))
    })
    LikeResponse deleteLike(
            @Schema(hidden = true) @RequestHeader(HttpHeaders.USER_ID) String currentUserId,
            @Parameter(description = "ID поста", example = "550e8400-e29b-41d4-a716-446655440000")
            @PathVariable String postId);
}