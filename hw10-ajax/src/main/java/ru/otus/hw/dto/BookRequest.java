package ru.otus.hw.dto;

import java.util.Set;

public record BookRequest(String title, String authorId, Set<String> genresIds) {}
