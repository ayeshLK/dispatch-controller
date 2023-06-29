package io.ayesh.sample.exceptions;

public class UnsupportedDroneStateException extends RuntimeException {
    public UnsupportedDroneStateException(String message) {
        super(message);
    }
}
