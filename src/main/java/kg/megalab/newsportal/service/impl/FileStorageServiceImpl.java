package kg.megalab.newsportal.service.impl;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.errors.*;
import kg.megalab.newsportal.exceptions.FileStorageException;
import kg.megalab.newsportal.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileStorageServiceImpl implements FileStorageService {

    private final MinioClient minioClient;

    @Value("${minio.bucket.name}")
    private String bucket;

    private String getFullFileName(MultipartFile file, String path) {
        String fileName = file.getOriginalFilename();
        assert fileName != null;

        String fileExtension = fileName.substring(fileName.lastIndexOf("."));
        return path + UUID.randomUUID().toString() + fileExtension;
    }

    @Override
    public String uploadImage(MultipartFile file, String path) {
        String fullFileName = getFullFileName(file, path);
        try {
            minioClient.putObject(
                    PutObjectArgs.builder()
                        .bucket(bucket)
                        .object(fullFileName)
                        .stream(file.getInputStream(), file.getSize(), -1)
                        .build());
            return fullFileName;
        } catch (IOException | ErrorResponseException | InsufficientDataException |
                InternalException | InvalidKeyException | InvalidResponseException |
                NoSuchAlgorithmException | ServerException | XmlParserException e) {

            throw new FileStorageException("File Storage error");
        }
    }

    @Override
    public void deleteImage(String fileName) {
        try {
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                        .bucket(bucket)
                        .object(fileName)
                        .build());
        } catch (ErrorResponseException | InsufficientDataException | InternalException |
                InvalidKeyException | InvalidResponseException | IOException |
                NoSuchAlgorithmException | ServerException | XmlParserException e) {

            throw new FileStorageException("File Storage error");
        }
    }
}
