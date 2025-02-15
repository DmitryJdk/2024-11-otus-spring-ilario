package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.Fields;
import org.springframework.stereotype.Service;
import ru.otus.hw.dto.AuthorDto;
import ru.otus.hw.mapper.LibraryMapper;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AuthorServiceImpl implements AuthorService {

    private final LibraryMapper libraryMapper;

    private final MongoTemplate template;

    @Override
    public List<AuthorDto> findAll() {
        //работают оба способа, есть ли разница ?
//        return template.query(Book.class)
//                .distinct("author.fullName")
//                .as(Author.class)
//                .all()
//                .stream()
//                .map(libraryMapper::authorToAuthorDto)
//                .toList();
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.project("author.fullName"),
                Aggregation.group("fullName"),
                Aggregation.project(Fields.from(Fields.field("fullName", "_id")))
        );

        return template.aggregate(aggregation, Book.class, Author.class).getMappedResults()
                .stream()
                .map(libraryMapper::authorToAuthorDto)
                .toList();
    }
}
