package io.ayesh.sample.exceptions;

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
        List<ErrorResponses.Violation> violations = argumentNotValid
                .getBindingResult()
                .getFieldErrors().stream()
                .map(fieldError -> new ErrorResponses.Violation(fieldError.getField(), fieldError.getDefaultMessage()))
                .toList();
        return new ErrorResponses.ValidationErrorResponse(violations);
    }
}
