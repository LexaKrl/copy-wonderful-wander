package com.technokratos.exception;

public class UsernameNotUniqueException extends ConflictServiceException{
    public UsernameNotUniqueException(String username) {
        super("The user with username: %s already exists".formatted(username));
    }
}
