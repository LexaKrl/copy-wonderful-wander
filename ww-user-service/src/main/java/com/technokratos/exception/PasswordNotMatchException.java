package com.technokratos.exception;

public class PasswordNotMatchException extends ConflictServiceException {
    public PasswordNotMatchException(String message) {
        super(message);
    }
}
