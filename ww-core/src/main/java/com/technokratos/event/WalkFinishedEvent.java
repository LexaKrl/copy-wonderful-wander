package com.technokratos.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WalkFinishedEvent {
    UUID walkId;
    UUID walkOwnerId;
    String walkName;
}
