package com.example.conversion.exception;

import com.example.conversion.model.FileType;

public class UnsupportedFileTypeException extends RuntimeException {
    public UnsupportedFileTypeException(FileType type) {
        super("No converter found for file type: " + type);
    }
}
