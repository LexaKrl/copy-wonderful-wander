package com.technokratos.dto.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@SuperBuilder
@Schema(description = "Базовая структура сообщения об ошибке")
public abstract class AbstractExceptionMessage {
    @Schema(description = "Время возникновения ошибки", example = "2025-04-05T14:30:00")
    @Builder.Default
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp = LocalDateTime.now();
    @Schema(description = "Тип ошибки", example = "MethodArgumentNotValidException")
    private String error;
    @Schema(description = "Статус ответа", example = "200")
    private int status;
}
