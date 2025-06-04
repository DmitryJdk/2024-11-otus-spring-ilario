package ru.home.ilar.mapper;

import org.mapstruct.Mapper;
import ru.home.ilar.dto.StockEntryDto;
import ru.home.ilar.entity.StockEntity;

@Mapper(componentModel = "spring")
public interface StockMapper {

    StockEntryDto stockEntityToEntryDto(StockEntity stockEntity);

}
