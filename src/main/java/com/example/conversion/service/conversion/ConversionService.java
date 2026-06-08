package com.example.conversion.service.conversion;

import com.example.conversion.exception.ConversionException;
import com.example.conversion.model.InputEvent;
import com.example.conversion.service.converter.FileConverter;
import com.example.conversion.service.converter.factory.ConverterFactory;
import com.example.conversion.service.storage.MinioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ConversionService {
    private final MinioService minioService; //для загрузки файлов
    private final ConverterFactory converterFactory; //выбирает нужный конвертер

    public String convert(InputEvent event) {
        InputStream inputFile = minioService.download(event.getFilePath()); // содержимое файла
        FileConverter converter = converterFactory.getConverter(event.getType());
        InputStream pdfFile;
        try {
            pdfFile = converter.convert(inputFile);
        } catch (Exception e) {
            throw new ConversionException("Failed to convert file", e);
        }
        String outputFile = "output/" + event.getEventId() + ".pdf";
        minioService.upload(outputFile, pdfFile);
        return outputFile;
    }
}
