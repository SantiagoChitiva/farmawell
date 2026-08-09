package com.example.farmawell;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class FarmawellApplication {

    public static void main(String[] args) {
        SpringApplication.run(FarmawellApplication.class, args);
    }

}