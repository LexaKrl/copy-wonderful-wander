package com.technokratos.exception;

public class UsernameNotUniqueException extends BadRequestServiceException{
    public UsernameNotUniqueException(String username) {
        super("The user with username: %s already exists".formatted(username));
    }
}
