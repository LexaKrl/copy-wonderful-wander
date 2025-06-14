package com.technokratos.exception;


public class CommentByIdNotFoundException extends NotFoundServiceException {
    public CommentByIdNotFoundException(String commentId) {
        super("Comment with id = %s - not found".formatted(commentId));
    }
}
