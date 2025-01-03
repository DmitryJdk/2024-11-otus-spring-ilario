package ru.otus.hw.service;

import org.springframework.stereotype.Component;
import ru.otus.hw.domain.Question;

@Component
public class QuestionConverter {

    public String convert(Question question) {
        var outputBuilder = new StringBuilder();
        outputBuilder.append(question.text()).append("\n");
        for (int i = 0; i < question.answers().size(); i++) {
            var answer = question.answers().get(i);
            outputBuilder.append("\t")
                    .append(i + 1)
                    .append(". ")
                    .append(answer.text())
                    .append("\n");
        }
        return outputBuilder.toString();
    }
}
