package com.technokratos.exception;

import org.springframework.http.HttpStatus;

import java.util.UUID;

public class WalkNotFoundException extends WalkServiceException {

    public WalkNotFoundException(UUID walkId) {
        super("Walk not found with UUID: %s".formatted(walkId), HttpStatus.NOT_FOUND);
    }
}
