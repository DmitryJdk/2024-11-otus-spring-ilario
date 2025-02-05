package ru.otus.hw.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.hw.models.Book;

import javax.annotation.Nonnull;
import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    @Nonnull
    @EntityGraph("Book.author")
    List<Book> findAll();
}
