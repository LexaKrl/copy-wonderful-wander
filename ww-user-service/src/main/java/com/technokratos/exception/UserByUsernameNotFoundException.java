package com.technokratos.exception;

public class UserByUsernameNotFoundException extends NotFoundServiceException {
    public UserByUsernameNotFoundException(String username) {
        super("User with username = %s - not found".formatted(username));
    }
}
