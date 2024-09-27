package br.univesp.ocorrencia_api.service;

import io.minio.*;
import io.minio.errors.*;
import io.minio.http.Method;
import lombok.NonNull;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class MinioService {

    private static final Logger LOGGER = Logger.getLogger(MinioService.class.getName());

    private MinioService() {
    }

    private static MinioClient buildMinioClient() {
        return MinioClient.builder()
                .endpoint("localhost", 9090, false)
                .credentials("admin", "admin")
                .build();
    }

    public static void uploadFile(String bucketName, MultipartFile file) {
        try {
            MinioClient minioClient = buildMinioClient();

            // Creates the bucket if it does not exist
            boolean found = minioClient.bucketExists(BucketExistsArgs.builder()
                    .bucket(bucketName)
                    .build());
            if (!found) {
                minioClient.makeBucket(MakeBucketArgs.builder()
                        .bucket(bucketName)
                        .build());
            }

            // Uploads the file via InputStream
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(file.getOriginalFilename())
                    .stream(file.getInputStream(), file.getSize(), -1)
                    .build());
        } catch (MinioException | IOException | NoSuchAlgorithmException | InvalidKeyException e) {
            LOGGER.throwing(MinioService.class.getName(), "uploadFile", e);
        }
    }

    public static String getFileUrl(@NonNull String bucketName, @NonNull String fileName) {
        try {
            MinioClient minioClient = buildMinioClient();

            return minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                    .method(Method.GET)
                    .bucket(bucketName)
                    .object(fileName)
                    .expiry(30, TimeUnit.MINUTES)
                    .build());
        } catch (ErrorResponseException | InsufficientDataException | InternalException | InvalidKeyException |
                 InvalidResponseException | IOException | NoSuchAlgorithmException | XmlParserException |
                 ServerException e) {
            LOGGER.throwing(MinioService.class.getName(), "getFileUrl", e);
            return null;
        }
    }
}
