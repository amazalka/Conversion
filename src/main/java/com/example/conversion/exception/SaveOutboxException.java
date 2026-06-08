package com.example.conversion.exception;

public class SaveOutboxException extends RuntimeException {
    public SaveOutboxException(String message, Throwable e) {
        super(message, e);
    }
}
