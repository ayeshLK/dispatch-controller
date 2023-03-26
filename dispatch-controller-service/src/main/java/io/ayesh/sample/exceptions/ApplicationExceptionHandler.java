package io.ayesh.sample.exceptions;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@ControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ErrorResponses.CommonClientError onHttpMessageNotReadable(HttpMessageNotReadableException messageNotReadable) {
        return new ErrorResponses.CommonClientError(messageNotReadable.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ErrorResponses.ValidationErrorResponse onMethodArgumentNotValid(MethodArgumentNotValidException argumentNotValid) {
        List<ErrorResponses.Error> errors = argumentNotValid
                .getBindingResult()
                .getFieldErrors().stream()
                .map(fieldError -> new ErrorResponses.Error(fieldError.getField(), fieldError.getDefaultMessage()))
                .toList();
        return new ErrorResponses.ValidationErrorResponse(errors);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ErrorResponses.ValidationErrorResponse onConstraintViolation(ConstraintViolationException constraintViolation) {
        List<ErrorResponses.Error> errors = constraintViolation
                .getConstraintViolations().stream()
                .map(violation ->
                        new ErrorResponses.Error("id", violation.getMessage()))
                .toList();
        return new ErrorResponses.ValidationErrorResponse(errors);
    }
}
