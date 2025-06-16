package com.technokratos.api;

import com.technokratos.dto.exception.BaseExceptionMessage;
import com.technokratos.dto.exception.ValidationExceptionMessage;
import com.technokratos.dto.request.post.PostRequest;
import com.technokratos.dto.response.PageResponse;
import com.technokratos.dto.response.post.PostResponse;
import com.technokratos.util.HttpHeaders;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Positive;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(
        name = "Посты",
        description = "Основные методы для взаимодействия с постами пользователей"
)
@Validated
@RequestMapping(value = "/api/posts", produces = MediaType.APPLICATION_JSON_VALUE)
public interface PostApi {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Получить рекомендованные посты", description = "Возвращает список постов, рекомендованных для текущего пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Рекомендованные посты успешно получены",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = PostResponse.class))),
            @ApiResponse(responseCode = "401", description = "Пользователь не аутентифицирован",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = BaseExceptionMessage.class)))
    })
    PageResponse<PostResponse> getRecommendedPosts(
            @Schema(hidden = true) @RequestHeader(HttpHeaders.USER_ID) String currentUserId,
            @Parameter(description = "Номер страницы", example = "1")
            @Positive @RequestParam(required = false, defaultValue = "1") Integer page,
            @Parameter(description = "Размер страницы", example = "10")
            @RequestParam(required = false, defaultValue = "10") Integer size);

    @GetMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Получить посты текущего пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Посты пользователя успешно получены",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = PostResponse.class))),
            @ApiResponse(responseCode = "401", description = "Пользователь не аутентифицирован",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = BaseExceptionMessage.class)))
    })
    PageResponse<PostResponse> getCurrentUserPosts(
            @Schema(hidden = true) @RequestHeader(HttpHeaders.USER_ID) String currentUserId,
            @Parameter(description = "Номер страницы", example = "1")
            @Positive @RequestParam(required = false, defaultValue = "1") Integer page,
            @Parameter(description = "Размер страницы", example = "10")
            @RequestParam(required = false, defaultValue = "10") Integer size);

    @GetMapping("/saved")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Получить сохраненные посты текущего пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Сохраненные посты успешно получены",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = PostResponse.class))),
            @ApiResponse(responseCode = "401", description = "Пользователь не аутентифицирован",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = BaseExceptionMessage.class)))
    })
    PageResponse<PostResponse> getCurrentUserSavedPosts(
            @Schema(hidden = true) @RequestHeader(HttpHeaders.USER_ID) String currentUserId,
            @Parameter(description = "Номер страницы", example = "1")
            @Positive @RequestParam(required = false, defaultValue = "1") Integer page,
            @Parameter(description = "Размер страницы", example = "10")
            @RequestParam(required = false, defaultValue = "10") Integer size);

    @GetMapping("/users/{userId}/posts")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Получить посты пользователя по ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Посты пользователя успешно получены",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = PostResponse.class))),
            @ApiResponse(responseCode = "400", description = "Невалидный uuid пользователя",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ValidationExceptionMessage.class))),
            @ApiResponse(responseCode = "401", description = "Пользователь не аутентифицирован",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = BaseExceptionMessage.class))),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = BaseExceptionMessage.class)))
    })
    PageResponse<PostResponse> getPostsByUserId(
            @Schema(hidden = true) @RequestHeader(HttpHeaders.USER_ID) String currentUserId,
            @Parameter(description = "ID пользователя", example = "550e8400-e29b-41d4-a716-446655440000")
            @PathVariable String userId,
            @Parameter(description = "Номер страницы", example = "1")
            @Positive @RequestParam(required = false, defaultValue = "1") Integer page,
            @Parameter(description = "Размер страницы", example = "10")
            @RequestParam(required = false, defaultValue = "10") Integer size);

    @GetMapping("/users/{userId}/saved")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Получить сохраненные посты пользователя по ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Сохраненные посты успешно получены",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = PostResponse.class))),
            @ApiResponse(responseCode = "400", description = "Невалидный uuid пользователя",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ValidationExceptionMessage.class))),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = BaseExceptionMessage.class)))
    })
    PageResponse<PostResponse> getSavedPostsByUserId(
            @Schema(hidden = true) @RequestHeader(HttpHeaders.USER_ID) String currentUserId,
            @Parameter(description = "ID пользователя", example = "550e8400-e29b-41d4-a716-446655440000")
            @PathVariable String userId,
            @Parameter(description = "Номер страницы", example = "1")
            @Positive @RequestParam(required = false, defaultValue = "1") Integer page,
            @Parameter(description = "Размер страницы", example = "10")
            @RequestParam(required = false, defaultValue = "10") Integer size);

    @GetMapping("/{postId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Получить пост по ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пост успешно получен",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = PostResponse.class))),
            @ApiResponse(responseCode = "400", description = "Невалидный uuid поста",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ValidationExceptionMessage.class))),
            @ApiResponse(responseCode = "404", description = "Пост не найден",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = BaseExceptionMessage.class)))
    })
    PostResponse getPostById(
            @Schema(hidden = true) @RequestHeader(HttpHeaders.USER_ID) String currentUserId,
            @Parameter(description = "ID поста", example = "550e8400-e29b-41d4-a716-446655440000")
            @PathVariable String postId);

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Создать новый пост")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Пост успешно создан",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = PostResponse.class))),
            @ApiResponse(responseCode = "400", description = "Ошибка валидации данных поста",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ValidationExceptionMessage.class))),
            @ApiResponse(responseCode = "401", description = "Пользователь не аутентифицирован",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = BaseExceptionMessage.class)))
    })
    PostResponse createPost(
            @Schema(hidden = true) @RequestHeader(HttpHeaders.USER_ID) String currentUserId,
            @Parameter(description = "Данные для создания поста", required = true)
            @RequestBody @Validated PostRequest createPostRequest);

    @PutMapping("/{postId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Обновить существующий пост")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пост успешно обновлен",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = PostResponse.class))),
            @ApiResponse(responseCode = "400", description = "Ошибка валидации данных поста или невалидный uuid поста",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ValidationExceptionMessage.class))),
            @ApiResponse(responseCode = "401", description = "Пользователь не аутентифицирован",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = BaseExceptionMessage.class))),
            @ApiResponse(responseCode = "403", description = "Нет прав для редактирования поста",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = BaseExceptionMessage.class))),
            @ApiResponse(responseCode = "404", description = "Пост не найден",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = BaseExceptionMessage.class)))
    })
    PostResponse updatePost(
            @Schema(hidden = true) @RequestHeader(HttpHeaders.USER_ID) String currentUserId,
            @Parameter(description = "ID поста", example = "550e8400-e29b-41d4-a716-446655440000")
            @PathVariable String postId,
            @Parameter(description = "Данные для обновления поста", required = true)
            @RequestBody @Validated PostRequest updatePostRequest);

    @DeleteMapping("/{postId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Удалить пост")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Пост успешно удален"),
            @ApiResponse(responseCode = "400", description = "Невалидный uuid поста",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ValidationExceptionMessage.class))),
            @ApiResponse(responseCode = "401", description = "Пользователь не аутентифицирован",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = BaseExceptionMessage.class))),
            @ApiResponse(responseCode = "403", description = "Нет прав для удаления поста",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = BaseExceptionMessage.class))),
            @ApiResponse(responseCode = "404", description = "Пост не найден",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = BaseExceptionMessage.class)))
    })
    void deletePost(
            @Schema(hidden = true) @RequestHeader(HttpHeaders.USER_ID) String currentUserId,
            @Parameter(description = "ID поста", example = "550e8400-e29b-41d4-a716-446655440000")
            @PathVariable String postId);

    @PostMapping("/saved/{postId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Сохранить пост пост к себе")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Пост успешно сохранен",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UUID.class))),
            @ApiResponse(responseCode = "400", description = "Невалидный uuid поста",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ValidationExceptionMessage.class))),
            @ApiResponse(responseCode = "401", description = "Пользователь не аутентифицирован",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = BaseExceptionMessage.class))),
            @ApiResponse(responseCode = "404", description = "Пост не найден",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = BaseExceptionMessage.class))),
            @ApiResponse(responseCode = "409", description = "Пост уже сохранен",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = BaseExceptionMessage.class)))
    })
    UUID savePost(
            @Schema(hidden = true) @RequestHeader(HttpHeaders.USER_ID) String currentUserId,
            @Parameter(description = "ID поста", example = "550e8400-e29b-41d4-a716-446655440000")
            @PathVariable String postId);

    @DeleteMapping("/saved/{postId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Удалить пост из своих сохраненок")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Пост успешно удален"),
            @ApiResponse(responseCode = "400", description = "Невалидный uuid поста",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ValidationExceptionMessage.class))),
            @ApiResponse(responseCode = "401", description = "Пользователь не аутентифицирован",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = BaseExceptionMessage.class))),
            @ApiResponse(responseCode = "403", description = "Нет прав для удаления поста из сохраненок",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = BaseExceptionMessage.class))),
            @ApiResponse(responseCode = "404", description = "Пост не найден",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = BaseExceptionMessage.class)))
    })
    void deleteSavedPost(
            @Schema(hidden = true) @RequestHeader(HttpHeaders.USER_ID) String currentUserId,
            @Parameter(description = "ID поста", example = "550e8400-e29b-41d4-a716-446655440000")
            @PathVariable String postId);
}
