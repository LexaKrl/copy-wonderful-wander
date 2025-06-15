package com.technokratos.entity;

import com.technokratos.enums.user.WalkVisibility;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "user_walk_visibility_settings")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserWalkVisibility {

    @Id
    @Column(name = "user_id")
    private UUID userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "walk_visibility", nullable = false)
    private WalkVisibility walkVisibility = WalkVisibility.PUBLIC;
}
