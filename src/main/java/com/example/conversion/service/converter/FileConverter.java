package com.example.conversion.service.converter;

import com.example.conversion.model.FileType;

import java.io.InputStream;

public interface FileConverter {
    FileType getType();

    InputStream convert(InputStream inputStream) throws Exception;
}
