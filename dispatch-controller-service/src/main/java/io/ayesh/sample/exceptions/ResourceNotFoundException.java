package io.ayesh.sample.exceptions;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String resource, String id) {
        super(String.format("%s resource for id %s could not be found", resource, id));
    }
}
