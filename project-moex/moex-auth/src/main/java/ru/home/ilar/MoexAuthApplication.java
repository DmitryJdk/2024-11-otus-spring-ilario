package ru.home.ilar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@SpringBootApplication
@EnableR2dbcRepositories(basePackages = "ru.home.ilar.repository")
public class MoexAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(MoexAuthApplication.class, args);
    }
}
