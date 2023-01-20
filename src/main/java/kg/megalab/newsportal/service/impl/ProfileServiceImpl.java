package kg.megalab.newsportal.service.impl;

import kg.megalab.newsportal.dto.request.ProfileInfoDto;
import kg.megalab.newsportal.dto.response.data.UserNewsDto;
import kg.megalab.newsportal.dto.response.data.UserView;
import kg.megalab.newsportal.dto.response.status.SuccessStatusResponse;
import kg.megalab.newsportal.exceptions.UsernameNotUniqueException;
import kg.megalab.newsportal.model.User;
import kg.megalab.newsportal.repository.NewsRepository;
import kg.megalab.newsportal.repository.UsersRepository;
import kg.megalab.newsportal.service.AuthService;
import kg.megalab.newsportal.service.FileStorageService;
import kg.megalab.newsportal.service.ProfileService;
import kg.megalab.newsportal.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final NewsRepository newsRepository;
    private final UsersRepository usersRepository;
    private final TokenService tokenService;
    private final AuthService authService;
    private final FileStorageService fileStorageService;

    @Override
    public UserView getUserProfile() {
        return usersRepository.findViewById(tokenService.getUserIdFromToken());
    }

    private void checkUpdatedUsername(String oldUsername, String newUsername) {
        if (!oldUsername.equals(newUsername) &&
                usersRepository.findByUsername(newUsername).isPresent())
            throw new UsernameNotUniqueException("Such username is taken");
    }

    @Override
    public ResponseEntity<SuccessStatusResponse> updateProfileInfo(ProfileInfoDto profileInfoDto) {
        User user = authService.getAuthenticatedUser();
        checkUpdatedUsername(user.getUsername(), profileInfoDto.getUsername());

        user.setProfileInfo(
                profileInfoDto.getFirstName(),
                profileInfoDto.getLastName(),
                profileInfoDto.getUsername()
        );

        usersRepository.save(user);
        return ResponseEntity.ok(new SuccessStatusResponse());
    }

    @Override
    public Page<UserNewsDto> getAllUserNews(String search, Pageable pageable) {
        return newsRepository.findAllUserNews(tokenService.getUserIdFromToken(), search, pageable);
    }

    @Override
    public ResponseEntity<SuccessStatusResponse> uploadProfileAvatar(MultipartFile file) {
        User user = authService.getAuthenticatedUser();
        if (user.getAvatar() != null)
            fileStorageService.deleteImage(user.getAvatar());

        String fullFileName = fileStorageService.uploadImage(file, "avatars/");

        user.setAvatar(fullFileName);
        usersRepository.save(user);

        return ResponseEntity.ok(new SuccessStatusResponse());
    }

    @Override
    public ResponseEntity<SuccessStatusResponse> deleteProfileAvatar() {
        User user = authService.getAuthenticatedUser();
        if (user.getAvatar() != null) {
            fileStorageService.deleteImage(user.getAvatar());

            user.setAvatar(null);
            usersRepository.save(user);
        }
        return ResponseEntity.ok(new SuccessStatusResponse());
    }
}
