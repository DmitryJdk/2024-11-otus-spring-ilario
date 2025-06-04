package ru.home.ilar.dto;

public record StockEntryDto(
        String ticket,
        String name,
        Double weight
) implements Comparable<StockEntryDto> {

    @Override
    public int compareTo(StockEntryDto o) {
        return o.weight.compareTo(this.weight);
    }
}


