package ru.otus.hw.sql.repository;

import jakarta.annotation.Nonnull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.hw.sql.model.Book;

import java.util.List;

public interface SqlBookRepository extends JpaRepository<Book, Long> {

    @Nonnull
    @EntityGraph(attributePaths = "author")
    List<Book> findAll();
}
