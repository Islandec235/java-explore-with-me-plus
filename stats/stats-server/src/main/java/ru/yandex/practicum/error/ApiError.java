package ru.yandex.practicum.error;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Setter
@Getter
public class ApiError {
    HttpStatus status;
    String reason;
    String message;
    String stackTrace;
    ZonedDateTime timestamp;

    public ApiError(HttpStatus status, String reason, String message, String stackTrace) {
        this.status = status;
        this.reason = reason;
        this.message = message;
        this.stackTrace = stackTrace;
        timestamp = ZonedDateTime.now();
    }
}
