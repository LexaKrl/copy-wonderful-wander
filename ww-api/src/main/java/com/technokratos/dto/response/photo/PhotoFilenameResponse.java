package com.technokratos.dto.response.photo;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO передающий имя загруженной фотографии")
public record PhotoFilenameResponse(
        @Schema(description = "Имя загруженной фотографии", example = "posts/fb611f58-1e89-4ffc-8ea5-754713cbda86.jpg")
        String filename) {
}
