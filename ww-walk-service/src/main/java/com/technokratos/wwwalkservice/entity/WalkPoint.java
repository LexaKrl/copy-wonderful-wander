package com.technokratos.wwwalkservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WalkPoint {
    private Double longitude;

    private Double latitude;

    private Integer steps;

    private Integer meters;

    private List<String> photos;

    private LocalDateTime time;
}
