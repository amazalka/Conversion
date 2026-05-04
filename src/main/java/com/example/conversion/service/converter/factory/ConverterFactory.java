package com.example.conversion.service.converter.factory;

import com.example.conversion.exception.UnsupportedFileTypeException;
import com.example.conversion.model.FileType;
import com.example.conversion.service.converter.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class ConverterFactory {
    private final List<FileConverter> fileConverterList;

    public FileConverter getConverter(FileType type) {
        return fileConverterList.stream()
                .filter(c -> c.getType() == type)
                .findFirst()
                .orElseThrow(() -> new UnsupportedFileTypeException(type));
    }
}
