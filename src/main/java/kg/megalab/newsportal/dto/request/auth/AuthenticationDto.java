package kg.megalab.newsportal.dto.request.auth;

import lombok.Data;

@Data
public class AuthenticationDto {

    private String username;
    private String password;
}
