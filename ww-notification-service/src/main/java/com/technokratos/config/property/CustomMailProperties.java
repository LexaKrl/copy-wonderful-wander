package com.technokratos.config.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Data
@Component
@ConfigurationProperties(prefix = "spring.mail")
public class CustomMailProperties {
    private String from;
}
