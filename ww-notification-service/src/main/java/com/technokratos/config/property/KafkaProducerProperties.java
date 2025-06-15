package com.technokratos.config.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "spring.kafka.producer")
public class KafkaProducerProperties {
    private String bootstrapServers;
    private String acks;
    private Integer retries;
    private Properties properties;

    @Data
    public static class Properties {
        private Integer deliveryTimeoutMs;
        private Integer lingerMs;
        private Integer requestTimeoutMs;
        private Boolean enableIdempotence;
        private Integer maxInFlightRequestsPerConnection;
    }
}
