package com.technokratos.wwwalkservice.exception;

import java.util.UUID;

public class WalkUpdateException extends WalkConflictServiceException {
    public WalkUpdateException(UUID id) {
        super("Couldn't update the walk that already started with id: %s".formatted(id));
    }

    public WalkUpdateException(UUID id, String message) {
        super("Couldn't update the walk with id: %s because %s".formatted(id, message));
    }
}
