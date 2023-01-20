package kg.megalab.newsportal.controller;

import io.minio.errors.*;
import kg.megalab.newsportal.dto.request.ProfileInfoDto;
import kg.megalab.newsportal.dto.response.data.UserNewsDto;
import kg.megalab.newsportal.dto.response.data.UserView;
import kg.megalab.newsportal.dto.response.status.SuccessStatusResponse;
import kg.megalab.newsportal.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping
    public UserView getUserProfile() {
        return profileService.getUserProfile();
    }

    @PutMapping
    public ResponseEntity<SuccessStatusResponse> updateProfileInfo(
            @Valid @RequestBody ProfileInfoDto profileInfoDto
    ) {
        return profileService.updateProfileInfo(profileInfoDto);
    }

    @GetMapping("/news")
    public Page<UserNewsDto> getAllUserNews(
            @RequestParam(required = false) String search, Pageable pageable
    ) {
        return profileService.getAllUserNews(search, pageable);
    }

    @PutMapping("/avatar")
    public ResponseEntity<SuccessStatusResponse> uploadProfileAvatar(
            @RequestPart("file") MultipartFile file
    ) {
        return profileService.uploadProfileAvatar(file);
    }

    @DeleteMapping("/avatar")
    public ResponseEntity<SuccessStatusResponse> deleteProfileAvatar() {
        return profileService.deleteProfileAvatar();
    }
}
