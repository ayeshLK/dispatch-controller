package io.ayesh.sample.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    private final String resource;
    private final String id;

    public ResourceNotFoundException(String resource, String id) {
        super(String.format("%s resource for id %s could not be found", resource, id));
        this.resource = resource;
        this.id = id;
    }
}
