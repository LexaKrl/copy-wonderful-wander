package com.technokratos.wwwalkservice.exception;

import java.util.UUID;

public class WalkStreamDataException extends WalkRecordDataConflictException {
    public WalkStreamDataException(UUID walkId) {
        super("Couldn't stream walk data with id: %s".formatted(walkId));
    }
}
