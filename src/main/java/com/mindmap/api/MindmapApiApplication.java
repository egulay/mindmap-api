package com.mindmap.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.reactive.config.EnableWebFlux;

@SpringBootApplication
@EnableReactiveMongoRepositories
@EnableWebFlux
@EnableConfigurationProperties
@EnableWebSecurity
public class MindmapApiApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(MindmapApiApplication.class, args);
    }
}

