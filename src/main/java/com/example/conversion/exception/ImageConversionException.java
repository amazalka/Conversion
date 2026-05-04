package com.example.conversion.exception;

public class ImageConversionException extends RuntimeException {
    public ImageConversionException(String message, Throwable e) {
        super(message, e);
    }
}
