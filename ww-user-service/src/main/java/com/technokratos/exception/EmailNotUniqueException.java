package com.technokratos.exception;


public class EmailNotUniqueException extends ConflictServiceException{
    public EmailNotUniqueException(String email) {
        super("The user with email: %s already exists".formatted(email));
    }
}
