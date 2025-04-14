package ru.otus.hw.sql.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.hw.sql.model.Genre;

public interface SqlGenreRepository extends JpaRepository<Genre, Long> {}
