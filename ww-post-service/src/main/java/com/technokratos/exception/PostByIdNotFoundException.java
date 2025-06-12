package com.technokratos.exception;

import java.util.UUID;

public class PostByIdNotFoundException extends NotFoundServiceException {
    public PostByIdNotFoundException(String postId) {
        super("Post with id = %s - not found".formatted(postId));
    }
}
