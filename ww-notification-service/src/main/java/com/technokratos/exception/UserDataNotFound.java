package com.technokratos.exception;

import org.springframework.http.HttpStatus;

import java.util.UUID;

public class UserDataNotFound extends ServiceException {
    public UserDataNotFound(UUID userId) {
        super("Couldn't find data of user with id: %s".formatted(userId), HttpStatus.NOT_FOUND);
    }
}
