package com.agrisight;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class AgriSightApplication {

    public static void main(String[] args) {
        SpringApplication.run(AgriSightApplication.class, args);
    }
}
