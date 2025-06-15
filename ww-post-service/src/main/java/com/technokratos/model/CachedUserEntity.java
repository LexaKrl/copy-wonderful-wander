package com.technokratos.model;

import com.technokratos.enums.user.PhotoVisibility;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Document(collection = "user_cache")
@Setter
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CachedUserEntity {
    @Id
    private String userId;
    private String username;
    private String avatarFilename;
    private PhotoVisibility myPhotoVisibility;
    private PhotoVisibility savedPhotoVisibility;
}
