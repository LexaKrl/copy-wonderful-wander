package com.technokratos.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "walk.record.location")
public class WalkRecordLocationProperties {
    private Long maxSseTimeout = 90000L;
    private Long reconnectionTime = 3000L;
    private Long maxWalkExpiresTime = 3L;
}
