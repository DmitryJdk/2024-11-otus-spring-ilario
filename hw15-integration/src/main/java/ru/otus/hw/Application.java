package ru.otus.hw;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.otus.hw.gateway.TransformationGateway;
import ru.otus.hw.model.Butterfly;
import ru.otus.hw.model.Caterpillar;
import ru.otus.hw.util.RandomDataUtils;

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
        for (int i = 0; i < 5; i++) {
            Caterpillar caterpillar = new Caterpillar(RandomDataUtils.getRandomSize());
            log.info("Начальная стадия, размер гусеницы: {}", caterpillar.getSize());
            Butterfly butterfly = gateway.transform(caterpillar);
            log.info("Конечная стадия, цвет бабочки: {}", butterfly.getColor());
        }
    }

}
