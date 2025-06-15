package com.technokratos.exception;

import java.util.UUID;

public class WalkParticipantOverflowException extends WalkConflictServiceException {
    public WalkParticipantOverflowException(UUID walkId) {
        super("Walk overflowed by participants with id: %s".formatted(walkId));
    }

    public WalkParticipantOverflowException(String message) {
        super(message);
    }
}
