package com.agrigo.ai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.agrigo.ai", "com.agrigo.common"})
public class AIRecommendationServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(AIRecommendationServiceApplication.class, args);
    }
}
