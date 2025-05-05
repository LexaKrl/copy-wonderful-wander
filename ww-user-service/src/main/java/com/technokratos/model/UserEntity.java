package com.technokratos.model;

import com.technokratos.enums.security.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
    private UUID id;
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private String email;
    private UserRole role;
}
