package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.util.exception.*;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

public class JdbcBeanValidateApi {

    public static void validate(Object o) {

        ValidatorFactory vf = Validation.buildDefaultValidatorFactory();
        Validator validator = vf.getValidator();

        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(o);

        if (constraintViolations.size() > 0) {
            throw new ValidationException(constraintViolations);
        }
    }
}
