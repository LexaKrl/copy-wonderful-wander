package ru.itis.danyook.exception;

import java.util.UUID;

public class PostByIdNotFoundException extends NotFoundServiceException {
    public PostByIdNotFoundException(UUID postId) {
        super("Post with id = %s - not found".formatted(postId));
    }
}
