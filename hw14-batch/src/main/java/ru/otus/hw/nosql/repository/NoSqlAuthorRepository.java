package ru.otus.hw.nosql.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.hw.nosql.model.Author;

public interface NoSqlAuthorRepository extends MongoRepository<Author, String> {}
