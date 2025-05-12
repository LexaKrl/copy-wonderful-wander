package com.technokratos.dto.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@SuperBuilder
@Schema(description = "Базовая структура сообщения об ошибке")
public abstract class AbstractExceptionMessage {
    @Schema(description = "Время возникновения ошибки", example = "2025-04-05T14:30:00")
    private LocalDateTime timestamp;
    @Schema(description = "Тип ошибки", example = "MethodArgumentNotValidException")
    private String error;
}
