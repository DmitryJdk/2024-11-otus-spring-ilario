package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.Fields;
import org.springframework.stereotype.Service;
import ru.otus.hw.dto.GenreDto;
import ru.otus.hw.mapper.LibraryMapper;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;

import java.util.List;

@RequiredArgsConstructor
@Service
public class GenreServiceImpl implements GenreService {

    private final LibraryMapper libraryMapper;

    private final MongoTemplate template;

    @Override
    public List<GenreDto> findAll() {
        //работают оба способа, есть ли разница ?
//        return template.query(Book.class)
//                .distinct("genres.name")
//                .as(Genre.class)
//                .all()
//                .stream()
//                .map(libraryMapper::genreToGenreDto)
//                .toList();
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.project("genres"),
                Aggregation.unwind("genres"),
                Aggregation.group("genres.name"),
                Aggregation.project(Fields.from(Fields.field("name", "_id")))
        );

        return template.aggregate(aggregation, Book.class, Genre.class).getMappedResults()
                .stream()
                .map(libraryMapper::genreToGenreDto)
                .toList();
    }
}
