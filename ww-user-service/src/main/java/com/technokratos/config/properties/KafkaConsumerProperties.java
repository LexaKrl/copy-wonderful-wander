package com.technokratos.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Data
@Component
@ConfigurationProperties("spring.kafka.consumer")
public class KafkaConsumerProperties {
    private String bootstrapServers;
    private String groupId;
    private Map<String, String> properties = new HashMap<>();

    public String getProps(String key) {
        return properties.get(key);
    }
}
