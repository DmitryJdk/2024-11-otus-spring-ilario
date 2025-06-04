package ru.home.ilar.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class AppConfiguration {

    @Bean
    public WebClient moexInformationClient(@Value("${moex.information.url}") String url) {
        return WebClient.create(url);
    }
}
