package ru.otus.hw.dto;

import java.util.Set;

public record BookDto(Long id, String title, String author, Set<String> genres) {}
