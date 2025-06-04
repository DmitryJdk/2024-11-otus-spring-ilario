package ru.home.ilar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MoexInformationApplication {

    public static void main(String[] args) {
        SpringApplication.run(MoexInformationApplication.class, args);
        System.out.println("App run: http://localhost:8082/api/info");
    }
}
