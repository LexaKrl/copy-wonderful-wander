package ru.itis.danyook.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Document(collection = "saved_posts")
public class SavedPostEntity {
    @Id
    private UUID id;

    @Indexed
    private UUID userId;

    @Indexed
    private UUID postId;

    private LocalDateTime savedAt;

}