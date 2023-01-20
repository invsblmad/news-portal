package kg.megalab.newsportal.service;

import kg.megalab.newsportal.dto.request.auth.AuthenticationDto;
import kg.megalab.newsportal.dto.request.auth.RegistrationDto;
import kg.megalab.newsportal.dto.response.TokenResponse;
import kg.megalab.newsportal.model.User;

public interface AuthService {

    TokenResponse login(AuthenticationDto authenticationDto);
    TokenResponse register(RegistrationDto registrationDto);
    User getAuthenticatedUser();
}
