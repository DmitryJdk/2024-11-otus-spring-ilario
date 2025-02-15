package ru.otus.hw.converters;

import org.springframework.stereotype.Component;
import ru.otus.hw.dto.AuthorDto;

@Component
public class AuthorConverter {
    public String authorToString(AuthorDto author) {
        return "FullName: " + author.fullName();
    }
}
