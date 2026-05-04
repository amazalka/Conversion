package com.example.conversion.exception;

public class TextConversionException extends RuntimeException {
    public TextConversionException(String message, Throwable e) {
        super(message, e);
    }
}
