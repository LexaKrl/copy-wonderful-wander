package com.technokratos.model;

import com.technokratos.enums.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {

    private UUID id;
    private String username;
    private String password;
    private String name;
    private String lastname;
    private String email;
    private UserRole role;
}
