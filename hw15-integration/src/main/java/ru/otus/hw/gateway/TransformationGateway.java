package ru.otus.hw.gateway;

import org.springframework.integration.annotation.MessagingGateway;
import ru.otus.hw.model.Butterfly;
import ru.otus.hw.model.Caterpillar;

@MessagingGateway(defaultRequestChannel = "inputChannel")
public interface TransformationGateway {

    Butterfly transform(Caterpillar input);

}
