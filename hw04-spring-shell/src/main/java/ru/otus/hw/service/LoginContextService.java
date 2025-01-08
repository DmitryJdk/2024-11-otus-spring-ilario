package ru.otus.hw.service;

import ru.otus.hw.domain.Student;

public interface LoginContextService {

    void setStudent(Student student);

    Student getStudent();

    boolean isLogged();
}
