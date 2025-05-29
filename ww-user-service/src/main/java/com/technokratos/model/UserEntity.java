package com.technokratos.model;

import com.technokratos.enums.security.UserRole;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserEntity {
    private UUID userId;
    private String username;
    private String password;
    private String email;
    private String firstname;
    private String lastname;
    private String bio;
    private UserRole role;
}
