package ru.otus.hw;

import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;
import ru.otus.hw.domain.Student;

import java.util.List;

public abstract class AbstractTest {

    protected final Student defaultTestStudent = new Student("name", "surname");

    protected final Question defaultTestQuestion = new Question(
            "Question?",
            List.of(
                    new Answer("Correct answer", true),
                    new Answer("Incorrect answer", false),
                    new Answer("Another incorrect answer", false)
            )
    );

}
