package com.technokratos.model;

import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Document(collection = "saved_post")
@Setter
public class SavedPostEntity {
    @Id
    private UUID id;

    @Indexed
    private UUID userId;

    @Indexed
    private UUID postId;

    private LocalDateTime savedAt;

}