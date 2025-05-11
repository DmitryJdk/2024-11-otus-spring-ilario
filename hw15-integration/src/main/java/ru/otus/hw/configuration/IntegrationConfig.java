package ru.otus.hw.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.messaging.MessageChannel;
import ru.otus.hw.model.Caterpillar;
import ru.otus.hw.model.Cocoon;
import ru.otus.hw.service.TransformationService;

@Configuration
@IntegrationComponentScan("ru.otus.hw.gateway")
public class IntegrationConfig {

    @Bean
    public MessageChannel inputChannel() {
        return new DirectChannel();
    }

    @Bean
    public IntegrationFlow transformFlow(TransformationService service) {
        return IntegrationFlow.from(inputChannel())
                .transform(Caterpillar.class, service::caterpillarToCocoon)
                .transform(Cocoon.class, service::cocoonToButterfly)
                .get();
    }
}