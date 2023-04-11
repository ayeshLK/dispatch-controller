package io.ayesh.sample.exceptions;

import io.ayesh.sample.responses.ServiceResponses;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(DataAccessException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    ServiceResponses.CommonErrorResponse onDataAccessException(DataAccessException dataAccessException) {
        return new ServiceResponses.CommonErrorResponse(dataAccessException.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ServiceResponses.CommonErrorResponse onHttpMessageNotReadable(HttpMessageNotReadableException messageNotReadable) {
        return new ServiceResponses.CommonErrorResponse(messageNotReadable.getMessage());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ServiceResponses.CommonErrorResponse onResourceNotFound(ResourceNotFoundException resourceNotFound) {
        return new ServiceResponses.CommonErrorResponse(resourceNotFound.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ServiceResponses.ValidationErrorResponse onMethodArgumentNotValid(
            MethodArgumentNotValidException argumentNotValid) {
        List<ServiceResponses.Error> errors = argumentNotValid
                .getBindingResult()
                .getFieldErrors().stream()
                .map(fieldError -> new ServiceResponses.Error(fieldError.getField(), fieldError.getDefaultMessage()))
                .toList();
        return new ServiceResponses.ValidationErrorResponse(errors);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ServiceResponses.ValidationErrorResponse onConstraintViolation(ConstraintViolationException constraintViolation) {
        List<ServiceResponses.Error> errors = constraintViolation
                .getConstraintViolations().stream()
                .map(violation ->
                        new ServiceResponses.Error(violation.getPropertyPath().toString(), violation.getMessage()))
                .toList();
        return new ServiceResponses.ValidationErrorResponse(errors);
    }

    @ExceptionHandler(UnsupportedDroneStateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ServiceResponses.CommonErrorResponse onUnsupportedDroneState(UnsupportedDroneStateException unsupportedDroneState) {
        return new ServiceResponses.CommonErrorResponse(unsupportedDroneState.getMessage());
    }

    @ExceptionHandler(DroneOverloadedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ServiceResponses.CommonErrorResponse onDroneOverloaded(DroneOverloadedException droneOverloaded) {
        return new ServiceResponses.CommonErrorResponse(droneOverloaded.getMessage());
    }
}
