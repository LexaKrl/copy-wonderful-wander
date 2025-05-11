package com.technokratos.controller.handler.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@SuperBuilder
public class ValidationExceptionMessage extends AbstractExceptionMessage {
    private List<Violation> violations;

    @Getter
    @Builder
    public static class Violation {
        private final String fieldName;
        private final String message;
    }
}
