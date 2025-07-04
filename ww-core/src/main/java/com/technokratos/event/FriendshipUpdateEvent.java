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
public class FriendshipUpdateEvent {

    private UUID userId;
    private UUID friendId;
    private FriendshipEventType type;

    public enum FriendshipEventType {
        ADD,
        DELETE
    }
}
