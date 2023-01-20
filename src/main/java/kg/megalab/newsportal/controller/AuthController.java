package kg.megalab.newsportal.controller;

import kg.megalab.newsportal.dto.request.auth.AuthenticationDto;
import kg.megalab.newsportal.dto.request.auth.RegistrationDto;
import kg.megalab.newsportal.dto.response.TokenResponse;
import kg.megalab.newsportal.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public TokenResponse login(@RequestBody AuthenticationDto authenticationDto) throws AuthenticationException {
        return authService.login(authenticationDto);
    }

    @PostMapping("/register")
    public TokenResponse register(@Valid @RequestBody RegistrationDto registrationDto) {
        return authService.register(registrationDto);
    }
}
