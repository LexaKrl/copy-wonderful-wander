package com.technokratos.dto.request.post;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

@Schema(description = "DTO для создания и обновления поста")
public record PostRequest(
        @Schema(description = "Подпись к фотографии", example = "Это мы в Дубае))",
                requiredMode = Schema.RequiredMode.REQUIRED)
        @Length(max = 128, message = "Title length should be 128 less characters")
        String title,
        @Schema(description = "Уникальный идентификатор фотографии UUID, по которому из облака можно получить ссылку на фото",
                example = "550e8400-e29b-41d4-a716-446655440000", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "Image filename is required")
        @Length(max = 128, message = "Image filename length should be less 128 characters")
        String imageFilename,
        @Schema(description = "Уникальный идентификатор категории", example = "234523423",
                requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull(message = "Category id is required")
        Long categoryId
) {
}
