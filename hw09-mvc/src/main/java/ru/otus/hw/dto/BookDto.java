package ru.otus.hw.dto;

import java.util.Set;

public record BookDto(String id, String title, String author, Set<String> genres) {}
