package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.entity.StockEntity;
import ru.otus.hw.repository.StockRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DatabaseStockService {

    private final StockRepository stockRepository;

    @Transactional
    public void saveStock(List<StockEntity> stockEntities) {
        stockRepository.deleteAll();
        stockRepository.flush();
        stockRepository.saveAll(stockEntities);
    }

    @Transactional(readOnly = true)
    public List<StockEntity> findAll() {
        return stockRepository.findAll();
    }
}
