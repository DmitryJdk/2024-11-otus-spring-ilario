package ru.home.ilar.service;

import ru.home.ilar.dto.StockEntryDto;

import java.util.List;

public interface MoexService {

    List<StockEntryDto> getCurrentStockIndex();
}
