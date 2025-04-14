package ru.otus.hw.nosql.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.hw.nosql.model.Book;

public interface NoSqlBookRepository extends MongoRepository<Book, String> {}
