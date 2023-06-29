package io.ayesh.sample.exceptions;

public class ResourceConflictException extends RuntimeException {

    public ResourceConflictException(String resource, String field, String value) {
        super(String.format("Resource %s has a conflict on %s: %s", resource, field, value));
    }
}
