package ru.home.ilar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class MoexIntegration {

    public static void main(String[] args) {
        SpringApplication.run(MoexIntegration.class, args);
        System.out.println("curl http://localhost:8081/api/currentStockIndex");
    }

}
