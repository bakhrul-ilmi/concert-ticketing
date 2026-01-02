package com.example.ticketing.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.persistence.autoconfigure.EntityScan;

@Configuration
@EntityScan({"com.example.ticketing.model"})
@EnableJpaRepositories({"com.example.ticketing.repository"})
public class ApplicationConfig {
    
}
