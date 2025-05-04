package ru.otus.hw.nosql.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.hw.nosql.model.NoSqlGenre;

public interface NoSqlGenreRepository extends MongoRepository<NoSqlGenre, String> {}