package kg.megalab.newsportal.dto.request.auth;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class RegistrationDto {

    @NotBlank(message = "The last name can't be null or empty")
    private String lastName;

    @NotBlank(message = "The first name can't be null or empty")
    private String firstName;

    @NotBlank(message = "The username can't be null or empty")
    private String username;

    @NotBlank(message = "The password can't be null or empty")
    @Size(min = 8, message = "The password must be at least 8 characters long")
    private String password;

    private String passwordConfirmation;
}
