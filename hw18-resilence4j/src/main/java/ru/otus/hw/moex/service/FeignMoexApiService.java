package ru.otus.hw.moex.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "imoex")
public interface FeignMoexApiService {

    @GetMapping("/statistics/engines/stock/markets/index/analytics/IMOEX.html?limit=100")
    String getCurrentIndexState();

}
