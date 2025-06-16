package com.technokratos.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Document(collection = "comment")
@Getter
@Setter
public class CommentEntity {

    @Id
    private String commentId;
    @Indexed
    private String postId;
    @Indexed
    private EmbeddedUser user;
    private String text;
    @Indexed
    private String rootCommentId;
    private String parentCommentUsername;
    private Long repliesCount;
    @Indexed
    private LocalDateTime createdAt;
}
