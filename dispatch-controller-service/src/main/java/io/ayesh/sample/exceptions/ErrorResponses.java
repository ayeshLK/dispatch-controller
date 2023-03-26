package io.ayesh.sample.exceptions;

import java.util.List;

public interface ErrorResponses {
    record CommonErrorResponse(String message){}
    record ValidationErrorResponse(List<Error> errors) {}
    record Error(String fieldName, String message) {}
}
