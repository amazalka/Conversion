package com.example.conversion.service.converter;

import com.example.conversion.exception.ImageConversionException;
import com.example.conversion.model.FileType;
import com.lowagie.text.Document;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

@Component("PNG")
public class PNGToPDFConverter implements FileConverter {
    @Override
    public FileType getType() {
        return FileType.PNG;
    }

    @Override
    public InputStream convert(InputStream inputStream) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Document PDFdocument = new Document();
        PdfWriter.getInstance(PDFdocument, out);
        PDFdocument.open();
        try {
            Image image = Image.getInstance(inputStream.readAllBytes());
            PDFdocument.add(image);
        } catch (Exception e) {
            throw new ImageConversionException("Failed to convert PNG to PDF", e);
        }
        PDFdocument.close();
        return new ByteArrayInputStream(out.toByteArray());
    }
}
