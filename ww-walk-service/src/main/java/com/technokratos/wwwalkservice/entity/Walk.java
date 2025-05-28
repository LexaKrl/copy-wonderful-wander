package com.technokratos.wwwalkservice.entity;


import com.technokratos.wwwalkservice.entity.enumuration.WalkStatus;
import jakarta.persistence.*;
/*import jakarta.validation.constraints.Size;*/
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

    @Column(name = "user_id", nullable = false)
    private UUID ownerId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "steps")
    private Integer totalSteps;

    @Column(name = "meters")
    private Integer totalMeters;

    @Column(name = "created_at", nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "finished_at")
    private LocalDateTime finishedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "walk_status")
    private WalkStatus walkStatus;

    @ElementCollection
    @CollectionTable(name = "walk_participants", joinColumns = @JoinColumn(name = "walk_id"))
    @Column(name = "participant_id")
    /*TODO @Size(max = 20, message = "Maximum 20 participants allowed")*/
    private Set<UUID> walkParticipants;

    @ElementCollection
    @CollectionTable(name = "walk_photos", joinColumns = @JoinColumn(name = "walk_id"))
    @Column(name = "photo")
    private Set<String> photos;

    // TODO consider add start point
}

