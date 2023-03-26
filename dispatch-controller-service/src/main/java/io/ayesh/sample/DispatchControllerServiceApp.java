package io.ayesh.sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DispatchControllerServiceApp {

    public static void main(String[] args) {
        SpringApplication.run(DispatchControllerServiceApp.class, args);
    }
}
