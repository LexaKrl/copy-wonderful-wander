package com.technokratos.dto.request.security;

public record PasswordChangeRequest(
        String oldPassword,
        String newPassword,
        String newDuplicatePassword) {
}

