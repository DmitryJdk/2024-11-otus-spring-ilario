package ru.home.ilar.dto;

import java.util.List;

public record StockSettingsDto(List<StockEntryDto> stock, List<UserIndexModificationItemDto> userSettings) {}
