package io.ayesh.sample.exceptions;

import java.util.List;

public interface ErrorResponses {
    record CommonClientError(String message){}
    record ValidationErrorResponse(List<Violation> violations) {}
    record Violation(String fieldName, String message) {}
}
