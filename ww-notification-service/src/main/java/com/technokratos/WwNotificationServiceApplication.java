package com.technokratos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@EnableRetry
@EnableFeignClients
@SpringBootApplication
public class WwNotificationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(WwNotificationServiceApplication.class, args);
    }

}
