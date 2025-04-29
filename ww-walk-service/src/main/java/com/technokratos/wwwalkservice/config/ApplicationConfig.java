package com.technokratos.wwwalkservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@Configuration
@EnableJpaRepositories("com.technokratos.wwwalkservice.repository")
@PropertySource("classpath:application.yaml")
public class ApplicationConfig {
}
