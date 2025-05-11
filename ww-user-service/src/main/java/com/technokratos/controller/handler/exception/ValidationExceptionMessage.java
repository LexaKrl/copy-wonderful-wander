package com.technokratos.controller.handler.exception;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.Map;

@Getter
@SuperBuilder
public class ValidationExceptionMessage extends AbstractExceptionMessage {
    private Map<String, String> fieldErrors;
}
