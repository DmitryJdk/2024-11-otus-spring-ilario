package ru.otus.hw.nosql.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Set;

@Data
@Document("books")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"noSqlGenres", "noSqlAuthor"})
public class NoSqlBook {

    @Id
    private String id;

    private String title;

    @Field(name = "author")
    private NoSqlAuthor noSqlAuthor;

    @Field(name = "genres")
    private Set<NoSqlGenre> noSqlGenres;
}
