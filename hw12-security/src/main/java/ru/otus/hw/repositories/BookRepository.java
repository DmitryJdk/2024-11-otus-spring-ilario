package ru.otus.hw.repositories;

import jakarta.annotation.Nonnull;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.hw.models.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    @Nonnull
    @EntityGraph(attributePaths = "author")
    List<Book> findAll();

    @EntityGraph(attributePaths = {"author", "genres"}, type = EntityGraph.EntityGraphType.LOAD)
    Optional<Book> findById(long id);
}
