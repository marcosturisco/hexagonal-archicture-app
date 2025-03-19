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
//    @Bean
//    public org.springframework.boot.CommandLineRunner runner(ApplicationContext ctx) {
//        return args -> {
//            System.out.println("Beans in context:");
//            for (String beanName : ctx.getBeanDefinitionNames()) {
//                System.out.println(beanName);
//            }
//        };
//    }
}