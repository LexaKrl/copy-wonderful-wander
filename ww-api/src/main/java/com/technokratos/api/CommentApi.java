package com.technokratos.api;

import com.technokratos.dto.exception.BaseExceptionMessage;
import com.technokratos.dto.exception.ValidationExceptionMessage;
import com.technokratos.dto.request.post.CommentRequest;
import com.technokratos.dto.response.post.RootCommentResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(
        name = "Комментарии",
        description = "Основные методы для взаимодействия с комментариями пользователей"
)
@Validated
@RequestMapping(value = "/api/posts/{postId}/comments", produces = MediaType.APPLICATION_JSON_VALUE)
public interface CommentApi {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Получить комментарии к посту", description = "Возвращает список комментариев для указанного поста")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Комментарии поста успешно получены",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = RootCommentResponse.class))),
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
    List<RootCommentResponse> getCommentsByPostId(
            @Parameter(description = "ID поста", example = "550e8400-e29b-41d4-a716-446655440000")
            @PathVariable UUID postId);

    @GetMapping("/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Получить конкретный комментарий", description = "Возвращает комментарий по его ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Комментарий успешно получены",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = RootCommentResponse.class))),
            @ApiResponse(responseCode = "400", description = "Невалидный uuid поста или комментария",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ValidationExceptionMessage.class))),
            @ApiResponse(responseCode = "401", description = "Пользователь не аутентифицирован",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = BaseExceptionMessage.class))),
            @ApiResponse(responseCode = "404", description = "Пост или комментарий не найден",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = BaseExceptionMessage.class)))
    })
    List<RootCommentResponse> getCommentById(
            @Parameter(description = "ID поста", example = "550e8400-e29b-41d4-a716-446655440000")
            @PathVariable UUID postId,
            @Parameter(description = "ID комментария", example = "550e8400-e29b-41d4-a716-446655440000")
            @PathVariable UUID commentId);

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Добавить комментарий к посту", description = "Создает новый комментарий к указанному посту")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Пост успешно создан",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = RootCommentResponse.class))),
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
    List<RootCommentResponse> createComment(
            @Parameter(description = "ID поста", example = "550e8400-e29b-41d4-a716-446655440000")
            @PathVariable UUID postId,
            @Parameter(description = "Данные комментария", required = true)
            @RequestBody @Validated CommentRequest commentRequest);

    @PutMapping("/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Обновить комментарий", description = "Редактирует существующий комментарий")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Комментарий успешно обновлен",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = RootCommentResponse.class))),
            @ApiResponse(responseCode = "400", description = "Ошибка валидации данных комментария или невалидный uuid поста или комментария",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ValidationExceptionMessage.class))),
            @ApiResponse(responseCode = "401", description = "Пользователь не аутентифицирован",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = BaseExceptionMessage.class))),
            @ApiResponse(responseCode = "403", description = "Нет прав для редактирования комментария",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = BaseExceptionMessage.class))),
            @ApiResponse(responseCode = "404", description = "Пост или комментарий не найден",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = BaseExceptionMessage.class)))
    })
    List<RootCommentResponse> updateComment(
            @Parameter(description = "ID поста", example = "550e8400-e29b-41d4-a716-446655440000")
            @PathVariable UUID postId,
            @Parameter(description = "ID комментария", example = "550e8400-e29b-41d4-a716-446655440000")
            @PathVariable UUID commentId,
            @Parameter(description = "Обновленные данные комментария", required = true)
            @RequestBody @Validated CommentRequest commentRequest);

    @DeleteMapping("/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Удалить комментарий", description = "Удаляет существующий комментарий")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Комментарий успешно удален"),
            @ApiResponse(responseCode = "401", description = "Пользователь не аутентифицирован",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = BaseExceptionMessage.class))),
            @ApiResponse(responseCode = "404", description = "Пост или комментарий не найден",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = BaseExceptionMessage.class))),
            @ApiResponse(responseCode = "409", description = "Комментарий уже удален",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = BaseExceptionMessage.class)))
    })
    void deleteComment(
            @Parameter(description = "ID поста", example = "550e8400-e29b-41d4-a716-446655440000")
            @PathVariable UUID postId,
            @Parameter(description = "ID комментария", example = "550e8400-e29b-41d4-a716-446655440000")
            @PathVariable UUID commentId);
}
