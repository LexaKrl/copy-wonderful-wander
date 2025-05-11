package com.technokratos.controller.handler.exception;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class BaseExceptionMessage extends AbstractExceptionMessage {
    private String message;
}
