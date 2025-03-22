package com.turisco.learning.adapter.inbound.cli.exception;

public class InvalidCommandException extends RuntimeException {
    public InvalidCommandException(String message) {
        super(message);
    }
}
