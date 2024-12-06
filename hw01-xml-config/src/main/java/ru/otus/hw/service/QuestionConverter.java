package ru.otus.hw.service;

import ru.otus.hw.domain.Question;

public class QuestionConverter {

    public String convert(Question question) {
        var sb = new StringBuilder();
        sb.append(question.text()).append("\n");
        for (var answer : question.answers()) {
            sb.append("\t").append(answer.text()).append("\n");
        }
        return sb.toString();
    }
}
