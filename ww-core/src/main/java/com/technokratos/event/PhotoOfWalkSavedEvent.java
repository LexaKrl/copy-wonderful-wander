package com.technokratos.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PhotoOfWalkSavedEvent {
    private UUID walkId;
    private UUID ownerId;
    private String photoFilename;
}
