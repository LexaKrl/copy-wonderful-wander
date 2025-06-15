package com.technokratos.exception;

public class InvalidJwtException extends UnauthorizedException{
    public InvalidJwtException(String message) {
        super(message);
    }
}
