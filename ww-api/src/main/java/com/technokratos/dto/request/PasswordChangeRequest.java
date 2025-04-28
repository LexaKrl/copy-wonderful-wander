package com.technokratos.dto.request;

public record PasswordChangeRequest(
        String oldPassword,
        String newPassword,
        String newDuplicatePassword) {
}

