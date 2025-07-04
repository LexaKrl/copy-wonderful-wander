package com.technokratos.api;

import com.technokratos.dto.exception.BaseExceptionMessage;
import com.technokratos.dto.exception.ValidationExceptionMessage;
import com.technokratos.dto.request.user.UserRequest;
import com.technokratos.dto.response.PageResponse;
import com.technokratos.dto.response.user.UserCompactResponse;
import com.technokratos.dto.response.user.UserForPostResponse;
import com.technokratos.dto.response.user.UserProfileResponse;
import com.technokratos.dto.response.user.UserResponse;
import com.technokratos.util.HttpHeaders;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(
        name = "Пользователи",
        description = "Основные методы для взаимодействия с пользователем(-ями)"
)
@Validated
@RequestMapping(value = "/api/users", produces = MediaType.APPLICATION_JSON_VALUE)
public interface UserApi {

    @GetMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Получить данные профиля текущего аутентифицированного пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Профиль успешно получен",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UserResponse.class))),
            @ApiResponse(responseCode = "401", description = "Пользователь не аутентифицирован",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = BaseExceptionMessage.class))),
    })
    UserResponse getCurrentUserProfile(@Schema(hidden = true) @RequestHeader(HttpHeaders.USER_ID) UUID ownerId);

    @PutMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Обновить данные профиля текущего аутентифицированного пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Профиль успешно обновлён",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UserResponse.class))),
            @ApiResponse(responseCode = "400", description = "Некорректные данные профиля или ошибка валидации",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ValidationExceptionMessage.class))),
            @ApiResponse(responseCode = "401", description = "Пользователь не аутентифицирован",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = BaseExceptionMessage.class))),
    })
    UserResponse updateCurrentUser(
            @Schema(hidden = true) @RequestHeader(HttpHeaders.USER_ID) UUID ownerId,
            @Parameter(description = "Данные для обновления профиля пользователя", required = true)
            @RequestBody @Validated UserRequest userRequest);

    @DeleteMapping("/me")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Удалить текущего пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Пользователь успешно удалён"),
            @ApiResponse(responseCode = "401", description = "Пользователь не аутентифицирован",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = BaseExceptionMessage.class)))
    })
    void deleteCurrentUser(@Schema(hidden = true) @RequestHeader(HttpHeaders.USER_ID) UUID ownerId);

    @PostMapping("/me/follows/{targetUserId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Подписаться на другого пользователя",
            description = "Текущий пользователь подписывается на пользователя с указанным targetId"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Подписка успешно осуществлена"),
            @ApiResponse(responseCode = "400", description = "Ошибка валидации",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ValidationExceptionMessage.class))),
            @ApiResponse(responseCode = "401", description = "Пользователь не аутентифицирован",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = BaseExceptionMessage.class))),
            @ApiResponse(responseCode = "404", description = "Пользователь с указанным targetId не найден",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = BaseExceptionMessage.class))),
            @ApiResponse(responseCode = "409", description = "Конфликт при подписке",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = BaseExceptionMessage.class)))
    })
    void follow(
            @Schema(hidden = true) @RequestHeader(HttpHeaders.USER_ID) UUID ownerId,
            @Parameter(description = "Id пользователя, на которого нужно подписаться", example = "550e8400-e29b-41d4-a716-446655440000")
            @PathVariable UUID targetUserId);

    @DeleteMapping("/me/follows/{targetUserId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
            summary = "Отписаться от пользователя",
            description = "Текущий пользователь отписывается от пользователя с указанным targetId"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Подписка успешно удалена"),
            @ApiResponse(responseCode = "400", description = "Ошибка валидации",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ValidationExceptionMessage.class))),
            @ApiResponse(responseCode = "401", description = "Пользователь не аутентифицирован",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = BaseExceptionMessage.class))),
            @ApiResponse(responseCode = "404", description = "Пользователь с указанным targetId не найден",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = BaseExceptionMessage.class)))
    })
    void unfollow(
            @Schema(hidden = true) @RequestHeader(HttpHeaders.USER_ID) UUID ownerId,
            @Parameter(description = "ID пользователя, от которого нужно отписаться", example = "550e8400-e29b-41d4-a716-446655440000")
            @PathVariable UUID targetUserId);

    @GetMapping("/{targetUserId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Получить профиль любого пользователя по id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Профиль пользователя успешно получен",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UserProfileResponse.class))),
            @ApiResponse(responseCode = "400", description = "Ошибка валидации",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ValidationExceptionMessage.class))),
            @ApiResponse(responseCode = "401", description = "Пользователь не аутентифицирован",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = BaseExceptionMessage.class))),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = BaseExceptionMessage.class)))
    })
    UserProfileResponse getUserProfileById(
            @Schema(hidden = true) @RequestHeader(HttpHeaders.USER_ID) UUID ownerId,
            @Parameter(description = "ID пользователя", example = "550e8400-e29b-41d4-a716-446655440000")
            @PathVariable UUID targetUserId);

    @GetMapping("/{userId}/friends")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Получить список друзей пользователя",
            description = "Возвращает список друзей пользователя с пагинацией и сортировкой в порядке от старых друзей к новым"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список друзей успешно получен"),
            @ApiResponse(responseCode = "400", description = "Ошибка валидации",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ValidationExceptionMessage.class))),
            @ApiResponse(responseCode = "401", description = "Пользователь не аутентифицирован",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = BaseExceptionMessage.class))),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = BaseExceptionMessage.class)))
    })
    PageResponse<UserCompactResponse> getFriendsByUserId(
            @Parameter(description = "ID пользователя", example = "550e8400-e29b-41d4-a716-446655440000")
            @PathVariable UUID userId,
            @Parameter(description = "Номер страницы", example = "1")
            @Positive @RequestParam(required = false, defaultValue = "1")
            Integer page,
            @Parameter(description = "Размер страницы", example = "10")
            @RequestParam(required = false, defaultValue = "10") Integer size);

    @GetMapping("/{userId}/following")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Получить список пользователей, на которых подписан пользователь",
            description = "Возвращает список пользователей, на которых подписан указанный пользователь в порядке от старых подписок к новым"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список подписок успешно получен"),
            @ApiResponse(responseCode = "400", description = "Ошибка валидации",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ValidationExceptionMessage.class))),
            @ApiResponse(responseCode = "401", description = "Пользователь не аутентифицирован",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = BaseExceptionMessage.class))),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = BaseExceptionMessage.class)))
    })
    PageResponse<UserCompactResponse> getFollowingByUserId(
            @Parameter(description = "ID пользователя", example = "550e8400-e29b-41d4-a716-446655440000")
            @PathVariable UUID userId,
            @Parameter(description = "Номер страницы", example = "1")
            @Positive @RequestParam(required = false, defaultValue = "1")
            Integer page,
            @Parameter(description = "Размер страницы", example = "10")
            @RequestParam(required = false, defaultValue = "10") Integer size);

    @GetMapping("/{userId}/followers")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Получить список подписчиков пользователя",
            description = "Возвращает список пользователей, которые подписаны на данного пользователя в порядке от старых подписок к новым"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список подписчиков успешно получен"),
            @ApiResponse(responseCode = "400", description = "Ошибка валидации",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ValidationExceptionMessage.class))),
            @ApiResponse(responseCode = "401", description = "Пользователь не аутентифицирован",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = BaseExceptionMessage.class))),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = BaseExceptionMessage.class)))
    })
    PageResponse<UserCompactResponse> getFollowersByUserId(
            @Parameter(description = "ID пользователя", example = "550e8400-e29b-41d4-a716-446655440000")
            @PathVariable UUID userId,
            @Parameter(description = "Номер страницы", example = "1")
            @Positive @RequestParam(required = false, defaultValue = "1")
            Integer page,
            @Parameter(description = "Размер страницы", example = "10")
            @RequestParam(required = false, defaultValue = "10") Integer size);


    @GetMapping("/{userId}/tech")
    UserForPostResponse getUserById(@PathVariable UUID userId);

    @GetMapping("/{userId}/friends/tech")
    List<UUID> getFriendsByUserId(@PathVariable UUID userId);

}