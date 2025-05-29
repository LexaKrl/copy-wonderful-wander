package com.technokratos.dto.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@Schema(description = "Стандартная структура сообщения об ошибке")
public class BaseExceptionMessage extends AbstractExceptionMessage {
    @Schema(description = "Описание ошибки", example = "JSON parse error")
    private String message;
}