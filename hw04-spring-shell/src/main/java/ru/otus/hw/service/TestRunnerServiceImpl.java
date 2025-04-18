package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TestRunnerServiceImpl implements TestRunnerService {

    private final TestService testService;

    private final ResultService resultService;

    private final LoginContextService loginContextService;

    @Override
    public void run() {
        var student = loginContextService.getStudent();
        var testResult = testService.executeTestFor(student);
        resultService.showResult(testResult);
    }
}
