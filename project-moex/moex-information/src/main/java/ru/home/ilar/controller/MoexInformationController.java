package ru.home.ilar.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.List;

@RestController
public class MoexInformationController {

    private static final List<String> INFORMATION = List.of(
            "P/E (Price-to-Earnings) - Капитализация компании / Общая прибыль",
            "P/S (Price-to-Sales) - Капитализация компании / Общая выручка",
            "EPS (Earnings-per-Share) - Чистая прибыль / количество акций в обращении",
            "EV/EBITDA (Enterprise Value-to-EBITDA) - " +
                    "Стоимость компании / Прибыль до вычета процентов, налогов и амортизации",
            "Debt/EBITDA - Долг / Прибыль до вычета процентов, налогов и амортизации.",
            "ROE (Return-on-Equity) - Чистая прибыль / собственный капитал"
    );

    @GetMapping(value = "/api/info", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> getInformation() {
        return Flux.interval(Duration.ZERO, Duration.ofSeconds(10))
                .map(i -> INFORMATION.get((int) (i % INFORMATION.size())));
    }
}
