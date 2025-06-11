package com.technokratos.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Document(collection = "post")
@Setter
@Getter
public class PostEntity {

    @Id
    private UUID postId;
    private String title;
    private String imageId;
    @Indexed
    private EmbeddedCategory category;
    @Indexed
    private LocalDateTime createdAt;
    @Indexed
    private EmbeddedUser user;
    private Long likesCount;
    private Long commentsCount;
}


