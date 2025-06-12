package com.technokratos.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Document(collection = "like")
public class LikeEntity {

    @Id
    private String likeId;
    @Indexed
    private String postId;
    private EmbeddedUser user;
}
