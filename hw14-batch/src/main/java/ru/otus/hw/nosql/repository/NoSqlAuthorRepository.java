package ru.otus.hw.nosql.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.hw.nosql.model.NoSqlAuthor;

public interface NoSqlAuthorRepository extends MongoRepository<NoSqlAuthor, String> {}
