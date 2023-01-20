package kg.megalab.newsportal.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ProfileInfoDto {

    @NotBlank(message = "The last name can't be null or empty")
    private String lastName;

    @NotBlank(message = "The first name can't be null or empty")
    private String firstName;

    @NotBlank(message = "The username can't be null or empty")
    private String username;
}
