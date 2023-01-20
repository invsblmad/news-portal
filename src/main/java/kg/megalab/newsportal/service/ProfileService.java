package kg.megalab.newsportal.service;

import io.minio.errors.*;
import kg.megalab.newsportal.dto.request.ProfileInfoDto;
import kg.megalab.newsportal.dto.response.data.UserNewsDto;
import kg.megalab.newsportal.dto.response.data.UserView;
import kg.megalab.newsportal.dto.response.status.SuccessStatusResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public interface ProfileService {

    UserView getUserProfile();
    ResponseEntity<SuccessStatusResponse> updateProfileInfo(ProfileInfoDto profileInfoDto);
    Page<UserNewsDto> getAllUserNews(String search, Pageable pageable);
    ResponseEntity<SuccessStatusResponse> uploadProfileAvatar(MultipartFile file);
    ResponseEntity<SuccessStatusResponse> deleteProfileAvatar();
}
