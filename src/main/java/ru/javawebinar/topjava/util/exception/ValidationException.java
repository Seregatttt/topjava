package ru.javawebinar.topjava.util.exception;

import javax.validation.ConstraintViolation;
import java.util.Set;

public class ValidationException extends RuntimeException {
    private final Set<ConstraintViolation<Object>> violations;

    public ValidationException(Set<ConstraintViolation<Object>> violations) {
        super();
        this.violations = violations;
    }

    @Override
    public String getMessage() {
        StringBuilder message = new StringBuilder(Object.class + " validation error(s) occurred:");
        for (ConstraintViolation violation : violations) {
            message.append("\n\t").append(violation.getPropertyPath().toString()).append(violation.getMessage());
        }
        return message.toString();
    }
}