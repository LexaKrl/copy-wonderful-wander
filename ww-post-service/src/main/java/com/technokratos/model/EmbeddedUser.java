package com.technokratos.model;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EmbeddedUser {
    private String userId;
    private String username;
    private String avatarId;
}