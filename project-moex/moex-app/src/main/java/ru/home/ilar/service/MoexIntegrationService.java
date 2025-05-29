package ru.home.ilar.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ru.home.ilar.dto.StockEntryDto;
import ru.home.ilar.service.moex.FeignMoexIntegrationService;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class MoexIntegrationService {

    private final FeignMoexIntegrationService moexIntegrationService;

    public Mono<List<StockEntryDto>> getCurrentStockIndex() {
        CompletableFuture<List<StockEntryDto>> future = CompletableFuture
                .supplyAsync(moexIntegrationService::getCurrentStockIndex);
        return Mono.fromFuture(future);
    }
}
