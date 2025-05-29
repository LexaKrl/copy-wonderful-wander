package com.technokratos.api;


import com.technokratos.dto.exception.BaseExceptionMessage;
import com.technokratos.dto.request.security.PasswordChangeRequest;
import com.technokratos.dto.request.security.RefreshTokenRequest;
import com.technokratos.dto.request.security.UserLoginRequest;
import com.technokratos.dto.request.security.UserRegistrationRequest;
import com.technokratos.dto.response.security.AuthResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Tag(name = "Security", description = "Все методы связанные с security пользователя")
@RequestMapping(value = "/api/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public interface AuthApi {

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Регистрация пользователя",
            description = "Пользователь регистрируется с данными из тела запроса"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь успешно зарегестрирован"),
            @ApiResponse(responseCode = "400", description = "Некорректные данные для регистрации",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = BaseExceptionMessage.class))),
            @ApiResponse(responseCode = "409", description = "Конфликтующие данные",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = BaseExceptionMessage.class)))
    })
    AuthResponse register(@RequestBody @Validated UserRegistrationRequest userDto);

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Аутентификация пользователя",
            description = "Пользователь входит в приложение с данными из тела запроса"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь успешно аутентифицирован"),
            @ApiResponse(responseCode = "400", description = "Некорректные данные для аутентификации",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = BaseExceptionMessage.class))),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = BaseExceptionMessage.class)))
    })
    AuthResponse login(@RequestBody @Validated UserLoginRequest userDto);

    @PostMapping("/refresh-token")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Обновление токена",
            description = "Обновляем Access token с помощью Refresh token"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Токен успешно обновлен"),
            @ApiResponse(responseCode = "401", description = "Пользователь не аутенцифицирован",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = BaseExceptionMessage.class))),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = BaseExceptionMessage.class)))
    })
    AuthResponse refreshToken(@RequestBody @Validated RefreshTokenRequest refreshTokenRequest);

    @PostMapping("/change-pass")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Обновление пароля",
            description = "Обновляем пароль пользователя"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Пароль успешно обновлен"),
            @ApiResponse(responseCode = "400", description = "Ошибка на уровне бизнес-логики",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = BaseExceptionMessage.class))),
            @ApiResponse(responseCode = "401", description = "Пользователь не аутенцифицирован",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = BaseExceptionMessage.class))),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = BaseExceptionMessage.class)))
    })
    void changePassword(@RequestBody @Validated PasswordChangeRequest passwordChangeRequest);
}
