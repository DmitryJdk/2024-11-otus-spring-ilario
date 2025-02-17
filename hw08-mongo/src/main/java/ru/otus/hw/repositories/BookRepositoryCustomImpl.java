package ru.otus.hw.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;

import java.util.List;

@RequiredArgsConstructor
public class BookRepositoryCustomImpl implements BookRepositoryCustom {

    private final MongoOperations template;

    @Override
    public List<Author> findAllAuthors() {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.project("author._id", "author.fullName"),
                Aggregation.group("_id").first("fullName").as("fullName")
        );

        return template.aggregate(aggregation, Book.class, Author.class).getMappedResults();
    }

    @Override
    public List<Genre> findAllGenres() {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.unwind("genres"),
                Aggregation.project("genres._id", "genres.name"),
                Aggregation.group("_id").first("name").as("name")
        );

        return template.aggregate(aggregation, Book.class, Genre.class).getMappedResults();
    }
}
