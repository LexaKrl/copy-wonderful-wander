package com.technokratos.wwwalkservice.exception;

import org.springframework.http.HttpStatus;

import java.util.UUID;

public class WalkSaveRepositoryException extends WalkRepositoryException {
    public WalkSaveRepositoryException(UUID walkId) {
        super("Exception during saving walk with id: %s".formatted(walkId), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
