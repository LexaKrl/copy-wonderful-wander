package ru.itis.danyook.model;

import com.technokratos.enums.user.PhotoVisibility;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;
import java.util.UUID;

@Document(collection = "user_cache")
@Setter
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CachedUserEntity {
    @Id
    private UUID userId;
    private String username;
    private String avatarUrl;
    private PhotoVisibility myPhotoVisibility;
    private PhotoVisibility savedPhotoVisibility;
    private Set<UUID> friends;
}
