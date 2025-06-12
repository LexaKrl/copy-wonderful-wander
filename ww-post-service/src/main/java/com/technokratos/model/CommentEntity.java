package com.technokratos.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Document(collection = "comment")
public class CommentEntity {

    @Id
    private String commentId;
    @Indexed
    private String postId;
    private EmbeddedUser user;
    private String text;
    @Indexed
    private LocalDateTime createdAt;
}
