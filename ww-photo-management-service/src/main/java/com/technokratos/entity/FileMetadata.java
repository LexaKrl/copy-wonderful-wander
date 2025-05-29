package com.technokratos.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class FileMetadata {
    private UUID fileId;
    private UUID ownerId;
    private String filename;
    private String extension;
    private Double size;
    private LocalDateTime uploadDateTime;
}
