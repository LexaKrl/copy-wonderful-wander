package com.technokratos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class WwWalkServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(WwWalkServiceApplication.class, args);
    }

}
