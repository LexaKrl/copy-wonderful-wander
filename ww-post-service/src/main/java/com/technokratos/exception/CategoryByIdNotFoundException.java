package com.technokratos.exception;

public class CategoryByIdNotFoundException extends NotFoundServiceException {
    public CategoryByIdNotFoundException(Long categoryId) {
        super("Category with id = %s - not found".formatted(categoryId));
    }
}
