package kg.megalab.newsportal.service.impl;

import kg.megalab.newsportal.dto.response.TokenResponse;
import kg.megalab.newsportal.security.CustomUserDetails;
import kg.megalab.newsportal.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final JwtEncoder encoder;

    @Override
    public TokenResponse generateToken(CustomUserDetails userDetails) {
        Instant now = Instant.now();
        String scope = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(1, ChronoUnit.HOURS))
                .subject(userDetails.getUsername())
                .id(String.valueOf(userDetails.getUser().getId()))
                .claim("scope", scope)
                .build();
        String jwtToken = this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
        return new TokenResponse(jwtToken);
    }

    @Override
    public Integer getUserIdFromToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken) && authentication != null
                && authentication.getPrincipal() instanceof Jwt) {
            Jwt jwt = (Jwt) authentication.getPrincipal();
            return Integer.parseInt(jwt.getId());
        } else {
            return null;
        }
    }
}
