package com.technokratos.dto.request;

public record UserLoginRequest(
        String username,
        String password) {
}
