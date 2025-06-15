package com.technokratos.exception;

import java.util.UUID;

public class WalkSaveUserException extends WalkConflictServiceException {
    public WalkSaveUserException(UUID userId) {
        super("Couldn't save user with id: %s".formatted(userId));
    }

    public WalkSaveUserException(UUID userId, String message) {
        super("Couldn't save user with id: %s because %s".formatted(userId, message));
    }
}
