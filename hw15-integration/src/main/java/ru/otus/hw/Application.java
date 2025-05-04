package ru.otus.hw;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.otus.hw.gateway.TransformationGateway;

@Slf4j
@SpringBootApplication
@RequiredArgsConstructor
public class Application implements CommandLineRunner {

    private final TransformationGateway gateway;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) {
        String caterpillar = "гусеница";
        log.info("Начальная стадия: {}", caterpillar);
        String result = gateway.transform(caterpillar);
        log.info("Конечная стадия: {}", result);

        String wrongInput = "не_гусеница";
        log.info("Начальная стадия: {}", wrongInput);
        String notChangedResult = gateway.transform(wrongInput);
        log.info("Конечная стадия: {}", notChangedResult);
    }

}
