package com.technokratos.event;

import com.technokratos.enums.user.PhotoVisibility;
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
public class UserCreatedEvent {
    private UUID userId;
    private String username;
    private String email;
    private String role;
    private String avatarFilename;
    private PhotoVisibility myPhotoVisibility;
    private PhotoVisibility savedPhotoVisibility;
    private WalkVisibility walkVisibility;
}
