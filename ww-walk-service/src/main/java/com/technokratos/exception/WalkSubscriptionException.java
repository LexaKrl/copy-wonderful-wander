package com.technokratos.exception;

import java.util.UUID;

public class WalkSubscriptionException extends WalkRecordDataConflictException {
    public WalkSubscriptionException(UUID walkId) {
        super("Couldn't subscribe to walk with id: %s".formatted(walkId));
    }
}
