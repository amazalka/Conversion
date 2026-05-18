package com.example.conversion.service.storage;

import com.example.conversion.exception.*;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;


@Service
@RequiredArgsConstructor
public class MinioService {
    private final MinioClient minioClient;
    @Value("${conversion.minio.bucket}")
    private String bucket;
    public InputStream download(String path) {
        try {
            return minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(bucket)
                            .object(path)
                            .build()
            );
        } catch (Exception e) {
            throw new FileNotFoundException(path);
        }
    }

    public void upload(String path, InputStream file) {
        try {
            byte[] bytes = file.readAllBytes();
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucket)
                            .object(path)
                            .stream(new ByteArrayInputStream(bytes), bytes.length, -1)
                            .contentType("application/pdf")
                            .build()
            );
        } catch (Exception e) {
            throw new MinIOUploadException(path);
        }
    }
}
