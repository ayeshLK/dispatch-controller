package io.ayesh.sample.responses;

import java.util.List;

public interface ServiceResponses {
    record CommonResponse(String message) {}
    record CommonErrorResponse(String message){}
    record ValidationErrorResponse(List<Error> errors) {}
    record Error(String fieldName, String message) {}
}
