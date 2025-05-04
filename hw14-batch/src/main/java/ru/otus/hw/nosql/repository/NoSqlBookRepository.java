package ru.otus.hw.nosql.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.hw.nosql.model.NoSqlBook;

public interface NoSqlBookRepository extends MongoRepository<NoSqlBook, String> {}
