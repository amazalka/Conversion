package com.example.conversion.service.converter;

import com.example.conversion.exception.TextConversionException;
import com.example.conversion.model.FileType;
import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfDocument;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Component("TXT")
public class TXTToPDFConverter implements FileConverter {
    @Override
    public FileType getType() {
        return FileType.TXT;
    }
    @Override
    public InputStream convert(InputStream inputStream) {
        ByteArrayOutputStream out = new ByteArrayOutputStream(); //контейнер в памяти, куда будет записываться PDF
        Document PDFdocument = new Document(); //создание документа
        PdfWriter.getInstance(PDFdocument, out); //все, что будет добавлено в документ, записывать в out
        PDFdocument.open();
        try {
            String text = new String(inputStream.readAllBytes()); // чтение inputStream (inputStream -> byte[] -> строка)
            PDFdocument.add(new Paragraph(text)); // кладем текст как абзац
        } catch (IOException e) {
            throw new TextConversionException("Failed to convert TXT to PDF", e);
        } finally {
            PDFdocument.close();
        }
        return new ByteArrayInputStream(out.toByteArray()); //возвращаем InputStream (byte[] -> inputStream)
    }
}
