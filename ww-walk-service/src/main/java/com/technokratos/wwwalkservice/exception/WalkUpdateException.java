package com.technokratos.wwwalkservice.exception;

import java.util.UUID;

public class WalkUpdateException extends WalkConflictServiceException {
    public WalkUpdateException(UUID id) {
        super("Couldn't update the walk that already started with id: %s".formatted(id));
    }
}
