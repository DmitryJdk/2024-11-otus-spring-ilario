package ru.home.ilar.service;

import ru.home.ilar.entity.StockEntity;

import java.util.List;

public interface DatabaseStockService {

    void saveStock(List<StockEntity> stockEntities);

    List<StockEntity> findAll();
}
