package com.technokratos.wwwalkservice.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "walk.record.location")
public class WalkRecordLocationProperties {
    private Long maxSseTimeout;
    private Long reconnectionTime;
}
