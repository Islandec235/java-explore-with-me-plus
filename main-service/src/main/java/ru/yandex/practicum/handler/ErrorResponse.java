package ru.yandex.practicum.handler;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Setter
@Getter
public class ErrorResponse {
    HttpStatus status;
    String message;
    String localizedMessage;
    String classInit;
    String stackTrace;
    ZonedDateTime dateTime;

    public ErrorResponse(HttpStatus status, String message, String localizedMessage, String classInit, String stackTrace) {
        this.status = status;
        this.message = message;
        this.localizedMessage = localizedMessage;
        this.classInit = classInit;
        this.stackTrace = stackTrace;
        dateTime = ZonedDateTime.now();
    }
}
