package ru.otus.hw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		System.out.println("App run: http://localhost:8080/");
		System.out.println("App run: http://localhost:8090/actuator");
		System.out.println("App run: http://localhost:8090/actuator/health");
		System.out.println("App run: http://localhost:8090/actuator/metrics");
	}

}
