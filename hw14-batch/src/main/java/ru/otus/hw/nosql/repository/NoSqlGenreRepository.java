package ru.otus.hw.nosql.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.hw.nosql.model.Genre;

public interface NoSqlGenreRepository extends MongoRepository<Genre, String> {}