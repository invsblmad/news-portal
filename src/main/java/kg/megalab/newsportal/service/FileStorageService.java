package kg.megalab.newsportal.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {

    String uploadImage(MultipartFile multipartFile, String path);
    void deleteImage(String fileName);
}
