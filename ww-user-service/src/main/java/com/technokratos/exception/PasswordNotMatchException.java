package com.technokratos.exception;

public class PasswordNotMatchException extends BadRequestServiceException {
    public PasswordNotMatchException(String message) {
        super(message);
    }
}
