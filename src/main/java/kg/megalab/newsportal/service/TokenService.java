package kg.megalab.newsportal.service;

import kg.megalab.newsportal.dto.response.TokenResponse;
import kg.megalab.newsportal.security.CustomUserDetails;

public interface TokenService {

    TokenResponse generateToken(CustomUserDetails userDetails);
    Integer getUserIdFromToken();
}
