package ru.otus.hw.sql.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.hw.sql.model.Author;

public interface SqlAuthorRepository extends JpaRepository<Author, Long> {}
