package ru.yandex.practicum.annotation;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(
        validatedBy = {ConstraintFutureInTwoHoursValidation.class}
)
public @interface ConstraintFutureInTwoHours {
    String message() default "Дата и время на которые намечено событие не может быть раньше," +
            " чем через два часа от текущего момента";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
