package ru.otus.hw.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class LibraryControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {EntityNotFoundException.class, IllegalArgumentException.class})
    public ResponseEntity<?> handleEntityNotFoundException (RuntimeException ex, WebRequest request) {
        var responseBody = """
                {"status": "failure"}
                """;
        return handleExceptionInternal(ex, responseBody, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }
}
