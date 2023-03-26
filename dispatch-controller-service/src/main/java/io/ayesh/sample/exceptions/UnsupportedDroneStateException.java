package io.ayesh.sample.exceptions;

public class UnsupportedDroneStateException extends Exception {
    public UnsupportedDroneStateException(String message) {
        super(message);
    }
}
