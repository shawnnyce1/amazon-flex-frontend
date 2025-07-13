package com.amazonflex;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AmazonFlexGrabberApplication {
    public static void main(String[] args) {
        SpringApplication.run(AmazonFlexGrabberApplication.class, args);
    }
}

