package com.technokratos.dto.response.post;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO представляющий данные о категории")
public record CategoryResponse(
        @Schema(description = "Уникальный идентификатор категории", example = "234523423")
        Long categoryId,
        @Schema(description = "Название категории", example = "Путешенствия")
        String name
) {}