package com.technokratos.wwwalkservice.entity;

import com.technokratos.wwwalkservice.entity.enumuration.WalkStatus;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "locations")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WalkLocationData {

    @Id
    private String id;

    private String walkId;

    private List<WalkPoint> points;
}
