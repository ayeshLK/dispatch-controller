package io.ayesh.sample.exceptions;

import io.ayesh.sample.responses.ServiceResponses;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataAccessException;
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

    @ExceptionHandler(DataAccessException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    ServiceResponses.CommonErrorResponse onDataAccessException(DataAccessException dataAccessException) {
        return new ServiceResponses.CommonErrorResponse(dataAccessException.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ServiceResponses.CommonErrorResponse onHttpMessageNotReadable(HttpMessageNotReadableException messageNotReadable) {
        return new ServiceResponses.CommonErrorResponse(messageNotReadable.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
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
    @ResponseBody
    ServiceResponses.ValidationErrorResponse onConstraintViolation(ConstraintViolationException constraintViolation) {
        List<ServiceResponses.Error> errors = constraintViolation
                .getConstraintViolations().stream()
                .map(violation ->
                        new ServiceResponses.Error("id", violation.getMessage()))
                .toList();
        return new ServiceResponses.ValidationErrorResponse(errors);
    }

    @ExceptionHandler(UnsupportedDroneStateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ServiceResponses.CommonErrorResponse onUnsupportedDroneState(UnsupportedDroneStateException unsupportedDroneState) {
        return new ServiceResponses.CommonErrorResponse(unsupportedDroneState.getMessage());
    }

    @ExceptionHandler(DroneOverloadedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ServiceResponses.CommonErrorResponse onDroneOverloaded(DroneOverloadedException droneOverloaded) {
        return new ServiceResponses.CommonErrorResponse(droneOverloaded.getMessage());
    }
}
