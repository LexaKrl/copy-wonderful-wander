package com.technokratos.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EmbeddedUser {
    private String userId;
    private String username;
    private String avatarFilename;
}