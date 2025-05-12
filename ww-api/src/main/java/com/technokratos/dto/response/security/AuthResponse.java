package com.technokratos.dto.response.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


public record AuthResponse(
        String accessToken,
        String refreshToken) {
}
