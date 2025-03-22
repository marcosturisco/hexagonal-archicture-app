package com.turisco.learning;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.turisco.learning")
public class App {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(App.class);
        app.setAdditionalProfiles("prod");
        app.run(args);
    }
}