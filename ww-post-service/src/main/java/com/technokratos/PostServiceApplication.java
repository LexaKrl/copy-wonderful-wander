package com.technokratos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.technokratos.client")
public class PostServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(PostServiceApplication.class, args);
	}

}
