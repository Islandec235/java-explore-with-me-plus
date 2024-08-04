package ru.yandex.practicum.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleCustomValueException(final CustomValueException e) {
        log.error("Error CustomValueException 400 {}", e.getMessage());
        return handleResponseCreate(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleMissingServletRequestParameterException(final MissingServletRequestParameterException e) {
        log.error("Error 400 {} \n {}", e.getMessage());
        return handleResponseCreate(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiError handleThrowable(final Throwable e) {
        log.error("Error Throwable 500 {}", e.getMessage());
        return handleResponseCreate(e, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    private ApiError handleResponseCreate(Throwable e, HttpStatus status) {
        String classInit = Arrays.stream(e.getStackTrace()).findAny().get().toString();
        String localMessage = e.getLocalizedMessage() + " \n Class: " + classInit;

        e.printStackTrace();
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        String stackTrace = sw.toString();

        return new ApiError(status, localMessage, e.getMessage(), stackTrace);
    }
}
