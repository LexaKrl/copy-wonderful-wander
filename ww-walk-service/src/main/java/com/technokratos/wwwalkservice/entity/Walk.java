package com.technokratos.wwwalkservice.entity;


import com.technokratos.enums.walk.WalkStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.Point;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "walks")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Walk {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "walk_id")
    private UUID walkId;

    @Column(name = "user_id", nullable = false)
    private UUID ownerId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    @Builder.Default
    private String description = "";

    @Column(name = "steps")
    @Builder.Default
    private Integer totalSteps = 0;

    @Column(name = "meters")
    @Builder.Default
    private Integer totalMeters = 0;

    @Column(name = "created_at", nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "finished_at")
    private LocalDateTime finishedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "walk_status")
    @Builder.Default
    private WalkStatus walkStatus = WalkStatus.NOT_STARTED;

    @ElementCollection
    @CollectionTable(name = "walk_participants", joinColumns = @JoinColumn(name = "walk_id"))
    @Column(name = "participant_id")
    @Builder.Default
    private Set<UUID> walkParticipants = new HashSet<>();

    @ElementCollection
    @CollectionTable(name = "walk_photos", joinColumns = @JoinColumn(name = "walk_id"))
    @Column(name = "photo")
    @Builder.Default
    private Set<String> photos = new HashSet<>();

    @Column(name = "start_point", columnDefinition = "geometry(Point, 4326)")
    private Point startPoint;

    @Column(name = "route", columnDefinition = "geometry(LineString, 4326)")
    private LineString route;
}

