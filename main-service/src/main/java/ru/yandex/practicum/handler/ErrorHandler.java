package ru.yandex.practicum.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.exception.ConflictException;
import ru.yandex.practicum.exception.IncorrectDateException;
import ru.yandex.practicum.exception.NotFoundException;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;


@Slf4j
@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFound(final NotFoundException e) {
        log.error("Error 404 {}", e.getMessage());
        String localMessage = e.getLocalizedMessage();
        String classInit = Arrays.stream(e.getStackTrace()).findAny().get().toString();

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        String stackTrace = sw.toString();

        return new ErrorResponse(
                HttpStatus.NOT_FOUND, e.getMessage(), localMessage, classInit, stackTrace
        );
    }

    @ExceptionHandler({IncorrectDateException.class, ConflictException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleConflict(final RuntimeException e) {
        log.error("Error 409 {}", e.getMessage());
        String localMessage = e.getLocalizedMessage();
        String classInit = Arrays.stream(e.getStackTrace()).findAny().get().toString();

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        String stackTrace = sw.toString();

        return new ErrorResponse(
                HttpStatus.CONFLICT, e.getMessage(), localMessage, classInit, stackTrace
        );
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleThrowable(final Throwable e) {
        log.error("Error Throwable 500 {}", e.getMessage());
        String localMessage = e.getLocalizedMessage();
        String classInit = Arrays.stream(e.getStackTrace()).findAny().get().toString();

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        String stackTrace = sw.toString();

        return new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), localMessage, classInit, stackTrace
        );
    }
}
