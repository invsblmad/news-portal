package kg.megalab.newsportal.service.impl;

import kg.megalab.newsportal.dto.request.auth.AuthenticationDto;
import kg.megalab.newsportal.dto.request.auth.RegistrationDto;
import kg.megalab.newsportal.dto.response.TokenResponse;
import kg.megalab.newsportal.exceptions.PasswordNotConfirmedException;
import kg.megalab.newsportal.exceptions.UsernameNotUniqueException;
import kg.megalab.newsportal.model.User;
import kg.megalab.newsportal.repository.UsersRepository;
import kg.megalab.newsportal.security.CustomUserDetails;
import kg.megalab.newsportal.service.AuthService;
import kg.megalab.newsportal.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UsersRepository usersRepository;

    @Override
    public TokenResponse login(AuthenticationDto authenticationDto) {
        Authentication authObject = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationDto.getUsername(), authenticationDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authObject);
        return tokenService.generateToken((CustomUserDetails) authObject.getPrincipal());
    }

    @Override
    public TokenResponse register(RegistrationDto registrationDto) {
        checkUserCredentials(registrationDto.getUsername(), registrationDto.getPassword(), registrationDto.getPasswordConfirmation());

        User user = new User(registrationDto.getFirstName(), registrationDto.getLastName(), registrationDto.getUsername(),
                passwordEncoder.encode(registrationDto.getPassword()));
        usersRepository.save(user);

        return tokenService.generateToken(new CustomUserDetails(user));
    }

    private void checkUserCredentials(String username, String password, String passwordConfirmation) {
        if (usersRepository.findByUsername(username).isPresent())
            throw new UsernameNotUniqueException("Such username is taken");

        if (!password.equals(passwordConfirmation))
            throw new PasswordNotConfirmedException("The password hasn't been confirmed");
    }

    @Override
    public User getAuthenticatedUser() {
        return usersRepository.findById(tokenService.getUserIdFromToken()).orElse(null);
    }
}

