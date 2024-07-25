package ru.yandex.practicum.related;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Setter
@Getter
public class ErrorResponse {
    HttpStatus status;
    String message;
    String stackTrace;
    ZonedDateTime dateTime;

    public ErrorResponse(HttpStatus status, String message, String stackTrace) {
        this.status = status;
        this.message = message;
        this.stackTrace = stackTrace;
        dateTime = ZonedDateTime.now();
    }
}
