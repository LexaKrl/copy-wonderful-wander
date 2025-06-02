package com.technokratos.api;

import com.technokratos.dto.exception.BaseExceptionMessage;
import com.technokratos.dto.exception.ValidationExceptionMessage;
import com.technokratos.dto.response.photo.PhotoFilenameResponse;
import com.technokratos.validation.image.Image;
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
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Tag(
        name = "Загрузка фотографий",
        description = "Методы для загрузки изображений: аватарок, постов и прогулок"
)
@RequestMapping(value = "/api/photos", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
public interface PhotoApi {

    @PostMapping("/avatar")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Загрузить фотографию для аватарки пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Аватарка успешно загружена"),
            @ApiResponse(responseCode = "400", description = "Неподдерживаемый тип фотографии",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ValidationExceptionMessage.class))),
            @ApiResponse(responseCode = "401", description = "Пользователь не аутентифицирован",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = BaseExceptionMessage.class)))
    })
    void uploadAvatar(
            @Parameter(description = "Файл изображения (JPG/JPEG, PNG, GIF, WEBP) size <= 25MB", required = true)
            @Validated @Image @RequestPart("avatar") MultipartFile avatar
    );


    @PostMapping("/post")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Загрузить фотографию для поста")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Фотография поста успешно загружена",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = PhotoFilenameResponse.class))),
            @ApiResponse(responseCode = "400", description = "Неподдерживаемый тип фотографии",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ValidationExceptionMessage.class))),
            @ApiResponse(responseCode = "401", description = "Пользователь не аутентифицирован",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = BaseExceptionMessage.class)))
    })
    PhotoFilenameResponse uploadPhotoOfPost(
            @Parameter(description = "Файл изображения (JPG/JPEG, PNG, GIF, WEBP) size <= 25MB", required = true)
            @Validated @Image @RequestPart("photo") MultipartFile photo
    );


    @PostMapping("/walk")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Загрузить фотографию для прогулки")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Фотография прогулки успешно загружена"),
            @ApiResponse(responseCode = "400", description = "Неподдерживаемый тип фотографии",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ValidationExceptionMessage.class))),
            @ApiResponse(responseCode = "401", description = "Пользователь не аутентифицирован",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = BaseExceptionMessage.class)))
    })
    void uploadPhotoOfWalk(
            @Parameter(description = "Идентификатор прогулки", example = "123e4567-e89b-12d3-a456-426614174000", required = true)
            @RequestParam UUID walkId,
            @Parameter(description = "Файл изображения (JPG/JPEG, PNG, GIF, WEBP) size <= 25MB", required = true)
            @Validated @Image @RequestPart("photo") MultipartFile photo
    );
}