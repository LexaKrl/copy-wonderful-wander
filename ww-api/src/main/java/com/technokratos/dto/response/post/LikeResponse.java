package com.technokratos.dto.response.post;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO представляющий количество лайков")
public record LikeResponse(
        @Schema(description = "Количество лайков у поста", example = "123")
        Long likesCount) {
}
