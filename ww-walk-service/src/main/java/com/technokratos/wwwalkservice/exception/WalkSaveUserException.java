package com.technokratos.wwwalkservice.exception;

import java.util.UUID;

public class WalkSaveUserException extends WalkConflictServiceException {
    public WalkSaveUserException(UUID userId) {
        super("Couldn't save user with id: %s".formatted(userId));
    }
}
