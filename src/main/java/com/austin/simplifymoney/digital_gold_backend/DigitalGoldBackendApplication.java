package com.austin.simplifymoney.digital_gold_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DigitalGoldBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(DigitalGoldBackendApplication.class, args);
    }
}
