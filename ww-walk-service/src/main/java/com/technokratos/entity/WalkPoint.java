package com.technokratos.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WalkPoint {
    private Double longitude;

    private Double latitude;

    private Integer steps;

    private Integer meters;

    private LocalDateTime time;
}
