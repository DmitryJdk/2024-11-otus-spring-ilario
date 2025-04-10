package ru.otus.hw.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@ControllerAdvice
public class LibraryControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {EntityNotFoundException.class, IllegalArgumentException.class})
    public Mono<ResponseEntity<Object>> handleEntityNotFoundException (RuntimeException ex, ServerWebExchange request) {
        var responseBody = """
                {"status": "failure"}
                """;
        return handleExceptionInternal(ex, responseBody, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }
}
