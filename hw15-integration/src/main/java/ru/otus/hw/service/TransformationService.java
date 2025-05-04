package ru.otus.hw.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TransformationService {

    public String caterpillarToCocoon(String input) {
        if ("гусеница".equalsIgnoreCase(input)) {
            log.info("Стадия 1: Преобразование в кокон");
            return "кокон";
        } else {
            return input;
        }
    }

    public String cocoonToButterfly(String input) {
        if ("кокон".equalsIgnoreCase(input)) {
            log.info("Стадия 2: Преобразование в бабочку");
            return "бабочка";
        } else {
            return input;
        }
    }
}
