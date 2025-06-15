package com.technokratos.wwwalkservice.exception;

import java.util.UUID;

public class WalkAccessDeniedException extends WalkForbiddenException {
    public WalkAccessDeniedException(UUID id) {
        super("Access denied for walk with id: %s".formatted(id));
    }
}
