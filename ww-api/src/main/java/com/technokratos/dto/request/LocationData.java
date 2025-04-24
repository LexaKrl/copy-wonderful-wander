package com.technokratos.dto.request;

import java.util.UUID;

public record LocationData(
        UUID walkId,
        Double longitude,
        Double latitude
) {
}
