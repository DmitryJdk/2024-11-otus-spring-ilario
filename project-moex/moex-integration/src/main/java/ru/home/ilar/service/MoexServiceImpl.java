package ru.home.ilar.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import ru.home.ilar.dto.StockEntryDto;
import ru.home.ilar.entity.StockEntity;
import ru.home.ilar.mapper.StockMapper;
import ru.home.ilar.moex.MoexAdapterService;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MoexServiceImpl implements MoexService {

    private final MoexAdapterService moexAdapterService;

    private final StockService stockService;

    private final StockMapper stockMapper;

    @Override
    public List<StockEntryDto> getCurrentStockIndex() {
        return getStockEntities().stream()
                .map(stockMapper::stockEntityToEntryDto)
                .sorted()
                .toList();
    }

    private List<StockEntity> getStockEntities() {
        try {
            String moexResponse = moexAdapterService.getCurrentIndexState();
            List<StockEntity> stockEntities = paresMoexAnswer(moexResponse);
            stockService.saveStock(stockEntities);
            return stockEntities;
        } catch (Exception e) {
            log.error("Error getting stock entities", e);
            return stockService.findAll();
        }
    }

    private List<StockEntity> paresMoexAnswer(String moexResponse) {
        Document doc = Jsoup.parse(moexResponse);
        List<StockEntity> stockEntities = new ArrayList<>();
        Elements rows = doc.selectXpath("//table[1]//tr[child::td]");
        for (Element row : rows) {
            String ticket = row.selectXpath("./td[3]").text();
            String name = row.selectXpath("./td[4]").text();
            Double weight = Double.parseDouble(row.selectXpath("./td[6]").text());
            StockEntity stockEntity = new StockEntity(ticket, name, weight);
            stockEntities.add(stockEntity);
        }
        return stockEntities;
    }

}
