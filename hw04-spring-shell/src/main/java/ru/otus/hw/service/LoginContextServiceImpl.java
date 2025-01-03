package ru.otus.hw.service;

import org.springframework.stereotype.Service;
import ru.otus.hw.domain.Student;

@Service
public class LoginContextServiceImpl implements LoginContextService {

    private Student student = null;

    @Override
    public boolean isLogged() {
        return student != null;
    }

    @Override
    public void setStudent(Student student) {
        this.student = student;
    }

    @Override
    public Student getStudent() {
        return student;
    }
}
