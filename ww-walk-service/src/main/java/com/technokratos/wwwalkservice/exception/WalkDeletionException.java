package com.technokratos.wwwalkservice.exception;

import java.util.UUID;

public class WalkDeletionException extends WalkConflictServiceException {
    public WalkDeletionException(UUID id) {
        super("Couldn't delete walk with id: %s".formatted(id));
    }
}
