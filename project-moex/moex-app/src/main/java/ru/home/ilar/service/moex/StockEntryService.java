package ru.home.ilar.service.moex;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import ru.home.ilar.dto.StockEntryDto;

import java.util.List;

@FeignClient(value = "moex-integration")
public interface StockEntryService {

    @GetMapping("/api/currentStockIndex")
    List<StockEntryDto> getCurrentStockIndex();
}
