package ru.home.ilar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class MoexApplication {

	public static void main(String[] args) {
		SpringApplication.run(MoexApplication.class, args);
		System.out.println("App run: http://localhost:8080/api/stockSettings");
		System.out.println("App run: http://localhost:8080/api/info");
		System.out.println("App run: http://localhost:8080/");
	}

}
