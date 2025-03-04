package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import ru.otus.hw.exceptions.QuestionReadException;

@RequiredArgsConstructor
public class TestRunnerServiceImpl implements TestRunnerService {

    private final TestService testService;

    private final IOService ioService;

    @Override
    public void run() {
        try {
            testService.executeTest();
        } catch (QuestionReadException e) {
            ioService.printLine("Error while reading csv file");
        } catch (Exception e) {
            ioService.printLine("Fatal error happened");
        }
    }
}
