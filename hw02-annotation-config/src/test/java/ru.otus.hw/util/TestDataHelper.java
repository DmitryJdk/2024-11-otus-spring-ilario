package ru.otus.hw.util;

import lombok.experimental.UtilityClass;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;
import ru.otus.hw.domain.Student;

import java.util.List;

@UtilityClass
public class TestDataHelper {

    public final static Student defaultTestStudent = new Student("name", "surname");

    public final static Question defaultTestQuestion = new Question(
            "Question?",
            List.of(
                    new Answer("Correct answer", true),
                    new Answer("Incorrect answer", false),
                    new Answer("Another incorrect answer", false)
            )
    );

}
