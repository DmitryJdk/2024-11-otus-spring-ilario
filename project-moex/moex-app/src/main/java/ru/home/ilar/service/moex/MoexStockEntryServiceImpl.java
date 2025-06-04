package ru.home.ilar.service.moex;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ru.home.ilar.dto.StockEntryDto;
import ru.home.ilar.service.StockIntegrationService;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class MoexStockEntryServiceImpl implements StockIntegrationService {

    private final StockEntryService stockEntryService;

    @Override
    public Mono<List<StockEntryDto>> getCurrentStockIndex() {
        CompletableFuture<List<StockEntryDto>> future = CompletableFuture
                .supplyAsync(stockEntryService::getCurrentStockIndex);
        return Mono.fromFuture(future);
    }
}
