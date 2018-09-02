package com.cars;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("entities")
public class CarsMicroServiceApp {

    public static void main(String[] args) {
        SpringApplication.run(CarsMicroServiceApp.class);
    }
}
