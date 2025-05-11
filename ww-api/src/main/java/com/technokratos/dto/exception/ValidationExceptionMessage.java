package com.technokratos.dto.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@SuperBuilder
@Schema(description = "Сообщение об ошибке валидации DTO или параметров запроса")
public class ValidationExceptionMessage extends AbstractExceptionMessage {
    @Schema(description = "Список нарушений валидации по полям")
    private List<Violation> violations;

    @Getter
    @Builder
    @Schema(description = "Нарушение ограничения валидации для конкретного поля")
    public static class Violation {
        @Schema(description = "Имя поля, в котором произошла ошибка", example = "email")
        private final String fieldName;
        @Schema(description = "Сообщение об ошибке валидации", example = "Email должен быть корректным")
        private final String message;
    }
}
