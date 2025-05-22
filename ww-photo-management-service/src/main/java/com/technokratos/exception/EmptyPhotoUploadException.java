package com.technokratos.exception;

public class EmptyPhotoUploadException extends BadRequestException {
    public EmptyPhotoUploadException() {
        super("Photo cannot be empty");
    }
}
