package ru.otus.hw.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Author {

    @Id
    private String id;

    private String fullName;

    public Author(String fullName) {
        this.id = fullName;
        this.fullName = fullName;
    }
}
