package ru.otus.hw.nosql.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("genres")
@AllArgsConstructor
@NoArgsConstructor
public class NoSqlGenre {

    @Id
    private String id;

    private String name;
}
