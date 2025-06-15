package com.technokratos.exception;

import org.springframework.http.HttpStatus;

import java.util.UUID;

public class WalkRecordDataNotFoundException extends WalkRecordDataServiceException {
    public WalkRecordDataNotFoundException(UUID walkId) {
        super("Walk record data not found with id: %s".formatted(walkId), HttpStatus.NOT_FOUND);
    }
}
