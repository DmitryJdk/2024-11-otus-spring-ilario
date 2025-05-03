package ru.otus.hw.moex;

import com.github.benmanes.caffeine.cache.Cache;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.otus.hw.moex.service.FeignMoexApiService;

@Slf4j
@Component
public class FeignMoexAdapter {

    private final FeignMoexApiService feignMoexApiService;

    private final Cache<String, String> moexCache;

    public FeignMoexAdapter(FeignMoexApiService feignMoexApiService,
                            @Qualifier("moexCache") Cache<String, String> moexCache) {
        this.feignMoexApiService = feignMoexApiService;
        this.moexCache = moexCache;
    }

    @Retry(name = "moexRetry", fallbackMethod = "currentIndexFallback")
    @RateLimiter(name = "moexRL", fallbackMethod = "currentIndexFallback")
    @CircuitBreaker(name = "moexCB", fallbackMethod = "currentIndexFallback")
    public String getCurrentIndexState() {
        String response = feignMoexApiService.getCurrentIndexState();
        moexCache.put("currentIndex", response);
        return response;
    }

    @SuppressWarnings("unused")
    private String currentIndexFallback(Exception ex) throws Exception {
        log.debug("Сработал метод fallback для moex, причина: {}", ex.getMessage());
        String cached = moexCache.getIfPresent("currentIndex");
        if (cached != null) {
            return cached;
        } else {
            throw ex;
        }
    }
}
