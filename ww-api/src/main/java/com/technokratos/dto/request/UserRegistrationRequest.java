package com.technokratos.dto.request;

public record UserRegistrationRequest(
        String username,
        String password,
        String email,
        String name,
        String lastname) {
}
