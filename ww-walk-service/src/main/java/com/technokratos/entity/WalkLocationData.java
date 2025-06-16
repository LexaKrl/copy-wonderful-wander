package com.technokratos.entity;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Document(collection = "locations")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WalkLocationData {

    @Id
    private UUID id;

    private UUID walkId;

    @Builder.Default
    private List<WalkPoint> points = new ArrayList<>();
}
