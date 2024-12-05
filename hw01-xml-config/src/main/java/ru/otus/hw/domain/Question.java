package ru.otus.hw.domain;

import java.util.List;

public record Question(String text, List<Answer> answers) {

    public String toString() {
        var sb = new StringBuilder();
        sb.append(text).append("\n");
        for (var answer : answers) {
            sb.append("\t").append(answer.text()).append("\n");
        }
        return sb.toString();
    }
}
