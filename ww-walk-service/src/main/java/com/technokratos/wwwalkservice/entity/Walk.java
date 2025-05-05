package com.technokratos.wwwalkservice.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "walks")
@Getter
@Setter
public class Walk {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "walk_id")
    private UUID walkId;

    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "steps")
    private Integer steps;

    @Column(name = "meters")
    private Integer meters;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
