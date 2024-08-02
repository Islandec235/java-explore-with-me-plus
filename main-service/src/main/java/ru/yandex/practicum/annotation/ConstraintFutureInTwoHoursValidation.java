package ru.yandex.practicum.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDateTime;

public class ConstraintFutureInTwoHoursValidation implements ConstraintValidator<ConstraintFutureInTwoHours, LocalDateTime> {

    @Override
    public boolean isValid(LocalDateTime localDateTime, ConstraintValidatorContext constraintValidatorContext) {

        return localDateTime == null || localDateTime.isAfter(LocalDateTime.now().withNano(0).plusHours(2));
    }
}
