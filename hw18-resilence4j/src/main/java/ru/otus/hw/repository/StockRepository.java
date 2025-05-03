package ru.otus.hw.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.hw.entity.StockEntity;

public interface StockRepository extends JpaRepository<StockEntity, Long> {}
