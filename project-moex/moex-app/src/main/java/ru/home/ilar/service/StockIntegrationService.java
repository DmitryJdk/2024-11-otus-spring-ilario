package ru.home.ilar.service;

import reactor.core.publisher.Mono;
import ru.home.ilar.dto.StockEntryDto;

import java.util.List;

public interface StockIntegrationService {

    Mono<List<StockEntryDto>> getCurrentStockIndex();
}
