package com.turisco.learning.domain.exception;

public class InvalidSpeciesException extends RuntimeException {
    public InvalidSpeciesException(String message) {
        super(message);
    }

    public InvalidSpeciesException(String message, Throwable cause) {
        super(message, cause);
    }
}
