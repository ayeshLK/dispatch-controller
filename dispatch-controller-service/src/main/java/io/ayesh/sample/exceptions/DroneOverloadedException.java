package io.ayesh.sample.exceptions;

public class DroneOverloadedException extends RuntimeException {
    public DroneOverloadedException(String message) {
        super(message);
    }
}
