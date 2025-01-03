package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Question;
import ru.otus.hw.domain.Student;
import ru.otus.hw.domain.TestResult;

@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private static final int ANSWERS_START_INDEX = 1;

    private static final int DEFAULT_ANSWER = -100;

    private final LocalizedIOService ioService;

    private final QuestionDao questionDao;

    private final QuestionConverter questionConverter;

    @Override
    public TestResult executeTestFor(Student student) {
        ioService.printLine("");
        ioService.printLineLocalized("TestService.answer.the.questions");
        ioService.printLine("");

        var questions = questionDao.findAll();
        var testResult = new TestResult(student);

        for (var question: questions) {
            ioService.printLine(questionConverter.convert(question));
            var answer = getAnswer(question);
            var isAnswerValid = validateAnswer(question, answer);
            testResult.applyAnswer(question, isAnswerValid);
        }
        return testResult;
    }

    private int getAnswer(Question question) {
        try {
            return ioService.readIntForRangeWithPromptLocalized(
                    ANSWERS_START_INDEX,
                    question.answers().size(),
                    "TestService.input.answer",
                    "TestService.input.retry"
            );
        } catch (IllegalArgumentException iae) {
            ioService.printLineLocalized("TestService.input.error");
            return DEFAULT_ANSWER;
        }
    }

    private boolean validateAnswer(Question question, int answer) {
        var answerIndex = answer - 1;
        if (answerIndex < 0 || answerIndex >= question.answers().size()) {
            return false;
        }
        return question.answers().get(answerIndex).isCorrect();
    }
}
