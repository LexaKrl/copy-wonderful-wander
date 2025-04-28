package com.technokratos.dto.request.security;

public record UserLoginRequest(
        String username,
        String password) {
}
