package com.technokratos.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Document(collection = "comments")
public class CommentEntity {

    @Id
    private UUID commentId;
    @Indexed
    private UUID postId;
    private EmbeddedUser user;
    private String text;

}
