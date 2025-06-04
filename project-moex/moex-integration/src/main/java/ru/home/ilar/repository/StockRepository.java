package ru.home.ilar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.home.ilar.entity.StockEntity;

public interface StockRepository extends JpaRepository<StockEntity, Long> {}
