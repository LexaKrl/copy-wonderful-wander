package ru.itis.danyook.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Document(collection = "post")
public class PostEntity {

    @Id
    private UUID postId;
    private String title;
    private String imageId;
    private long categoryId;
    @Indexed
    private LocalDateTime createdAt;
    @Indexed
    private EmbeddedUserEntity user;
    private long likesCount;
    private long commentsCount;

}


