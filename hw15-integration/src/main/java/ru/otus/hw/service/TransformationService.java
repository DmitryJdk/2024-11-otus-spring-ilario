package ru.otus.hw.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.hw.model.Butterfly;
import ru.otus.hw.model.Caterpillar;
import ru.otus.hw.model.Cocoon;
import ru.otus.hw.util.RandomDataUtils;

@Slf4j
@Service
public class TransformationService {

    public Cocoon caterpillarToCocoon(Caterpillar caterpillar) {
        Cocoon cocoon = new Cocoon(RandomDataUtils.getRandomWight(caterpillar.getSize()));
        log.info("Стадия 1: Преобразование в кокон, вес: {}", cocoon.getWeight());
        return cocoon;
    }

    public Butterfly cocoonToButterfly(Cocoon cocoon) {
        Butterfly butterfly = new Butterfly(RandomDataUtils.getRandomColor(cocoon.getWeight()));
        log.info("Стадия 2: Преобразование в бабочку, цвет: {}", butterfly.getColor());
        return butterfly;
    }
}
