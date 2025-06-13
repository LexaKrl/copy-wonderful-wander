package com.technokratos.model;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Document(collection = "like")
@Getter
@Builder
public class LikeEntity {

    @Id
    private String likeId;
    @Indexed
    private String postId;
    private EmbeddedUser user;
}
