package com.technokratos.wwwalkservice.entity;


import com.technokratos.wwwalkservice.entity.enumuration.WalkStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "walks")
@Data
@AllArgsConstructor
@NoArgsConstructor
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
    private Integer totalSteps;

    @Column(name = "meters")
    private Integer totalMeters;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "finished_at")
    private LocalDateTime finishedAt;

    @Column(name = "walk_status")
    private WalkStatus walkStatus;

    @ElementCollection
    @CollectionTable(name = "walk_participants")
    private Set<UUID> walkParticipants;

    @ElementCollection
    @CollectionTable(name = "walk_photos")
    private Set<UUID> photos;
}

