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

@Component("JPG")
public class JPGToPDFConverter implements FileConverter {
    @Override
    public FileType getType() {
        return FileType.JPG;
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
            throw new ImageConversionException("Failed to convert JPG to PDF", e);
        }
        PDFdocument.close();
        return new ByteArrayInputStream(out.toByteArray());
    }
}
