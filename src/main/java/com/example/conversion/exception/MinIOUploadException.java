package com.example.conversion.exception;

public class MinIOUploadException extends RuntimeException {
    public MinIOUploadException(String path) {
        super("Failed to upload file to MinIO: " + path);
    }
}
