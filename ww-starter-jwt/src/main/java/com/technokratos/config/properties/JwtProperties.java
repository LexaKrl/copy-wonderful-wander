package com.technokratos.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {
    private Boolean enabled;
    private long accessTokenDuration = 1000 * 60 * 60;
    private long refreshTokenDuration = 1000 * 60 * 60 * 24;
    private long timeTokenEndSoon = 15 * 60 * 1000;
    private String secretKey = "";
}
