package com.reunitex;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ReuniteXApplication {

    public static void main(String[] args) {
        SpringApplication app =
                new SpringApplication(ReuniteXApplication.class);

        app.setDefaultProperties(
                java.util.Map.of(
                        "server.port",
                        "8081"
                )
        );

        app.run(args);
    }
}