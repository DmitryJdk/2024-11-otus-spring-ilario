package ru.otus.hw.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import ru.otus.hw.domain.Student;
import ru.otus.hw.service.LocalizedIOService;
import ru.otus.hw.service.LoginContextService;
import ru.otus.hw.service.StudentService;
import ru.otus.hw.service.TestRunnerService;

@ShellComponent
@RequiredArgsConstructor
public class TestApplicationCommands {

    private final StudentService studentService;

    private final LoginContextService loginContextService;

    private final LocalizedIOService ioService;

    private final TestRunnerService testRunnerService;

    @ShellMethod(value = "Login command", key = {"l", "login"})
    public String login() {
        Student student = studentService.determineCurrentStudent();
        loginContextService.setStudent(student);
        return ioService.getMessage("Shell.command.welcome", student.firstName());
    }

    @ShellMethod(value = "Start test", key = {"s", "start"})
    @ShellMethodAvailability(value = "isStudentLogged")
    public void startTest() {
        testRunnerService.run();
    }

    private Availability isStudentLogged() {
        if (loginContextService.isLogged()) {
            return Availability.available();
        }
        var message = ioService.getMessage("Shell.command.login.first");
        return Availability.unavailable(message);
    }
}
