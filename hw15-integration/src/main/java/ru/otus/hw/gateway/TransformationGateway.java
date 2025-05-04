package ru.otus.hw.gateway;

import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway(defaultRequestChannel = "inputChannel")
public interface TransformationGateway {

    String transform(String input);

}
