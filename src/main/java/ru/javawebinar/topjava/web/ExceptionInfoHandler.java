package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import ru.javawebinar.topjava.util.ValidationUtil;
import ru.javawebinar.topjava.util.exception.ErrorInfo;
import ru.javawebinar.topjava.util.exception.ErrorType;
import ru.javawebinar.topjava.util.exception.IllegalRequestDataException;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import javax.servlet.http.HttpServletRequest;

import static ru.javawebinar.topjava.util.exception.ErrorType.*;

@RestControllerAdvice(annotations = RestController.class)
@Order(Ordered.HIGHEST_PRECEDENCE + 10)
public class ExceptionInfoHandler {
    private static Logger log = LoggerFactory.getLogger(ExceptionInfoHandler.class);

    //https://stackoverflow.com/questions/2109476/how-to-handle-dataintegrityviolationexception-in-spring
    @Autowired
    private MessageSource messageSource;

    //  http://stackoverflow.com/a/22358422/548473
    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(NotFoundException.class)
    public ErrorInfo handleError(HttpServletRequest req, NotFoundException e) {
        return logAndGetErrorInfo(req, e, false, DATA_NOT_FOUND);
    }

    @ResponseStatus(value = HttpStatus.CONFLICT)  // 409
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ErrorInfo conflict(HttpServletRequest req, DataIntegrityViolationException e) {
        String msg = e.getCause().getCause().getMessage();
        if (msg.contains("users_unique_email_idx")) {
            String errMsg = messageSource.getMessage("exception.users.duplicate_email", null, LocaleContextHolder.getLocale());
            return logAndGetErrorInfo(req, e, true, DATA_ERROR, errMsg);
        }
        if (msg.contains("meals_unique_user_datetime_idx")) {
            String errMsg = messageSource.getMessage("exception.meals.duplicate_datetime", null, LocaleContextHolder.getLocale());
            return logAndGetErrorInfo(req, e, true, DATA_ERROR, errMsg);
        }
        return logAndGetErrorInfo(req, e, true, DATA_ERROR);
    }

    //https://mkyong.com/spring-boot/spring-rest-validation-example/
    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)  // 422
    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class})
    public ErrorInfo notValidException(HttpServletRequest req, Exception e) {
        BindingResult result =
                (e instanceof MethodArgumentNotValidException) ?
                        ((MethodArgumentNotValidException) e).getBindingResult()
                        :
                        ((BindException) e).getBindingResult();

        String[] arrayError = result.getFieldErrors().stream()
                .map(fe -> String.format("[%s] %s", fe.getField(), fe.getDefaultMessage()))
                .toArray(String[]::new);
        return logAndGetErrorInfo(req, e, false, VALIDATION_ERROR, arrayError);
    }

    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)  // 422
    @ExceptionHandler({IllegalRequestDataException.class, MethodArgumentTypeMismatchException.class, HttpMessageNotReadableException.class})
    public ErrorInfo illegalRequestDataError(HttpServletRequest req, Exception e) {
        return logAndGetErrorInfo(req, e, false, VALIDATION_ERROR);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorInfo handleError(HttpServletRequest req, Exception e) {
        return logAndGetErrorInfo(req, e, true, APP_ERROR);
    }

    //    https://stackoverflow.com/questions/538870/should-private-helper-methods-be-static-if-they-can-be-static
    private static ErrorInfo logAndGetErrorInfo(HttpServletRequest req, Exception e, boolean logException,
                                                ErrorType errorType, String... arrayError) {
        Throwable rootCause = ValidationUtil.getRootCause(e);
        if (logException) {
            log.error(errorType + " at request " + req.getRequestURL(), rootCause);
        } else {
            log.warn("{} at request  {}: {}", errorType, req.getRequestURL(), rootCause.toString());
        }

        return new ErrorInfo(req.getRequestURL().toString(), errorType,
                arrayError.length > 0 ? arrayError : new String[]{rootCause.toString()});
    }
}