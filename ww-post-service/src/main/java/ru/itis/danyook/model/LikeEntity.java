package ru.itis.danyook.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Document(collection = "like")
public class LikeEntity {

    @Id
    private UUID likeId;
    @Indexed
    private UUID postId;
    private EmbeddedUser user;
}
