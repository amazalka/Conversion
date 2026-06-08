package com.example.conversion.service.converter;

import com.example.conversion.model.FileType;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Component("ZIP")
@RequiredArgsConstructor
public class ZIPToPDFConverter implements FileConverter {
    private final List<FileConverter> converters;

    @Override
    public FileType getType() {
        return FileType.ZIP;
    }

    @Override
    public InputStream convert(InputStream inputStream) throws Exception {
        List<byte[]> PDFparts = new ArrayList<>();
        ZipInputStream zip = new ZipInputStream(inputStream);
        ZipEntry zipEntry;
        while ((zipEntry = zip.getNextEntry()) != null) {
            if (!zipEntry.isDirectory()) {
                byte[] fileBytes = readAllBytes(zip);
                FileType type = detectType(zipEntry.getName());
                FileConverter converter = findConverter(type);
                byte[] pdf = converter.convert(new ByteArrayInputStream(fileBytes)).readAllBytes();
                PDFparts.add(pdf);
            }
            zip.closeEntry();
        }
        return new ByteArrayInputStream(mergePDFs(PDFparts));
    }

    private FileConverter findConverter(FileType type) {
        return converters.stream()
                .filter(c -> c.getType() == type)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No converter for " + type));
    }

    public byte[] readAllBytes(InputStream input) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int nRead;
        byte[] data = new byte[4096];
        while ((nRead = input.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }
        buffer.flush();
        return buffer.toByteArray();
    }

    private byte[] mergePDFs(List<byte[]> pdfs) {
        PDFMergerUtility pdfDocument = new PDFMergerUtility();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            for (byte[] pdf : pdfs) {
                pdfDocument.addSource(new ByteArrayInputStream(pdf));
            }
            pdfDocument.setDestinationStream(out);
            pdfDocument.mergeDocuments(MemoryUsageSetting.setupMainMemoryOnly());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return out.toByteArray();
    }

    private FileType detectType(String name) {
        String ext = name.substring(name.lastIndexOf(".") + 1).toLowerCase();
        return switch (ext) {
            case "txt" -> FileType.TXT;
            case "png" -> FileType.PNG;
            case "jpg", "jpeg" -> FileType.JPG;
            case "zip" -> FileType.ZIP;
            default -> throw new RuntimeException("Unsupported: " + ext);
        };
    }
}
