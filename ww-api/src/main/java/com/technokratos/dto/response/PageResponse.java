package com.technokratos.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;

@Schema(description = "Обёртка, представляющая информацию о пагинации класса DTO")
@Builder
public record PageResponse<T>(
        @Schema(description = "Пагинируемый класс")
        List<T> data,
        @Schema(description = "Общее доступное количество записей для получения", example = "100")
        int total,
        @Schema(description = "Количество записей в списке", example = "5")
        int limit,
        @Schema(description = "Сдвиг в получении записей", example = "20")
        int offset) {
}
