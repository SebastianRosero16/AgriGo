package com.agrigo.farmer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableFeignClients
@ComponentScan(basePackages = {"com.agrigo.farmer", "com.agrigo.common"})
public class FarmerServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(FarmerServiceApplication.class, args);
    }
}
