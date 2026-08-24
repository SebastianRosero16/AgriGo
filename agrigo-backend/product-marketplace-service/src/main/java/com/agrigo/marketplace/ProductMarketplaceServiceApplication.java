package com.agrigo.marketplace;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableFeignClients
@ComponentScan(basePackages = {"com.agrigo.marketplace", "com.agrigo.common"})
public class ProductMarketplaceServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProductMarketplaceServiceApplication.class, args);
    }
}
