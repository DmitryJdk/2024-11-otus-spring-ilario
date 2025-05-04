package ru.otus.hw.nosql.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("authors")
@AllArgsConstructor
@NoArgsConstructor
public class NoSqlAuthor {

    @Id
    private String id;

    private String fullName;
}
