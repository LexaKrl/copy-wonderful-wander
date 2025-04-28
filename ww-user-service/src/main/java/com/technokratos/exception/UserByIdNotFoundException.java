package com.technokratos.exception;

import java.util.UUID;

public class UserByIdNotFoundException extends NotFoundServiceException {
    public UserByIdNotFoundException(UUID userId) {
        super("User with id = %s - not found".formatted(userId));
    }
}
