package com.technokratos.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Data
@Component
@ConfigurationProperties(prefix = "spring.kafka.producer")
public class KafkaProducerProperties {
    private String bootstrapServers;
    private String acks;
    private String retries;
    private Map<String, String> properties = new HashMap<>();

    public String getProps(String key) {
        return properties.get(key);
    }
}
