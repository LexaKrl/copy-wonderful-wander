package com.technokratos.exception;

public class EmptyPhotoUploadException extends BadRequestException {
    public EmptyPhotoUploadException() {
        super("Image cannot be empty");
    }
}
