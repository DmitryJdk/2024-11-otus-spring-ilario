package ru.home.ilar.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.home.ilar.entity.StockEntity;
import ru.home.ilar.repository.StockRepository;

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
