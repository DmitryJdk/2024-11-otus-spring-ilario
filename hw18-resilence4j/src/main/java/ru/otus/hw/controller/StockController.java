package ru.otus.hw.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.hw.dto.StockEntryDto;
import ru.otus.hw.service.MoexService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class StockController {

    private final MoexService moexService;

    @GetMapping(value = "/currentStockIndex", produces = "application/json;charset=UTF-8")
    public List<StockEntryDto> getCurrentStockIndex() {
        return moexService.getCurrentStockIndex();
    }
}
