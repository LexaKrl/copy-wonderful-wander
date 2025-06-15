package com.technokratos.dto;


import com.technokratos.dto.enums.StarterUserRole;

import java.util.UUID;


public record StarterUserInfoForJwt(
        UUID userId,
        String username,
        StarterUserRole role) {
}
