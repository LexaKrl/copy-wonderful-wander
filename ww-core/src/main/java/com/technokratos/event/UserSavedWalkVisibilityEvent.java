package com.technokratos.event;

import com.technokratos.enums.user.WalkVisibility;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserSavedWalkVisibilityEvent {
    private UUID userId;
    private WalkVisibility walkVisibility;
}
