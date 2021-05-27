package com.certimeter.progetto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories
//Test: @ComponentScan(basePackages = {"com.*"})
@SpringBootApplication

public class ProgettoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProgettoApplication.class, args);
    }

}
