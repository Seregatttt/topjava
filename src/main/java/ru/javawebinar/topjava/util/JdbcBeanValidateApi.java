package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.util.exception.ValidationException;

import javax.validation.*;
import java.util.Set;

public class JdbcBeanValidateApi {
    static ValidatorFactory vf = Validation.buildDefaultValidatorFactory();
    static Validator validator = vf.getValidator();

    public static <T> void validate(T o) {
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(o);

        if (constraintViolations.size() > 0) {
            throw new ConstraintViolationException("Error in JdbcBeanValidateApi",constraintViolations);
        }
    }
}
