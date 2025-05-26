package com.technokratos.dto.request.post;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

public record PostRequest(
        @Schema(description = "Подпись к фотографии", example = "Это мы в Дубае))",
                requiredMode = Schema.RequiredMode.REQUIRED)
        String title,
        @Schema(description = "Уникальный идентификатор фотографии UUID, по которому из облака можно получить ссылку на фото",
                example = "550e8400-e29b-41d4-a716-446655440000", requiredMode = Schema.RequiredMode.REQUIRED)
        UUID imageId,
        @Schema(description = "Уникальный идентификатор категории", example = "234523423",
                requiredMode = Schema.RequiredMode.REQUIRED)
        long categoryId
) {
}
