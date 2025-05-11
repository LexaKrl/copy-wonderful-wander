package com.technokratos.controller.handler.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@SuperBuilder
public abstract class AbstractExceptionMessage {
    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();
    private String error;
}
