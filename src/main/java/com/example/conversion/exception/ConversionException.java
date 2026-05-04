package com.example.conversion.exception;

public class ConversionException extends RuntimeException {
    public ConversionException(String message, Throwable e) {
        super(message, e);
    }
}
