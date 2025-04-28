package ru.otus.hw.repositories;

import jakarta.annotation.Nonnull;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.hw.models.Author;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    @Nonnull
    List<Author> findAll();
}
