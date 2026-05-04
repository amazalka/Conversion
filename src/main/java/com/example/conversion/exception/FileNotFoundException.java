package com.example.conversion.exception;

public class FileNotFoundException extends RuntimeException {
    public FileNotFoundException(String path) {
        super("File not found in MinIO: " + path);
    }
}
