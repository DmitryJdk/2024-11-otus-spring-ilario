package ru.otus.hw.mapper;

import org.mapstruct.Mapper;
import ru.otus.hw.dto.StockEntryDto;
import ru.otus.hw.entity.StockEntity;

@Mapper(componentModel = "spring")
public interface StockMapper {

    StockEntryDto stockEntityToEntryDto(StockEntity stockEntity);

}
