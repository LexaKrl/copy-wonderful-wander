package com.technokratos.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Document(collection = "saved_post")
@Setter
@Getter
@ToString
public class SavedPostEntity {
    @Id
    private String savedPostId;

    @Indexed
    private String userId;

    @Indexed
    private String postId;

    private LocalDateTime savedAt;

}