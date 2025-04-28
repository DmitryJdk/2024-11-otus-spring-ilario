package ru.otus.hw.dto;

import java.util.Set;

public record BookDto(Long id, String title, AuthorDto author, Set<String> genres) {}
