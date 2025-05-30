package com.technokratos.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record PhotoOfWalkUploadRequest(
        UUID walkId,
        PhotoUploadRequest photoUploadRequest) {
}
