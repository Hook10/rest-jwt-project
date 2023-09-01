package com.hook.restjwtproject.dto;

import static com.hook.restjwtproject.utils.Constants.PASSWORD_NOT_BLANK;
import static com.hook.restjwtproject.utils.Constants.PASSWORD_PATTERN;
import static com.hook.restjwtproject.utils.Constants.USERNAME_NOT_BLANK;
import static com.hook.restjwtproject.utils.Constants.USERNAME_PATTERN;

import com.hook.restjwtproject.validator.PasswordMatching;
import com.hook.restjwtproject.validator.UserExists;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;

@Getter
@Setter
@PasswordMatching
@Valid
@Schema(description = "Entity of User")
public class UserRegistrationDto {

    @NotBlank(message = USERNAME_NOT_BLANK)
    @Pattern(regexp = USERNAME_PATTERN, message = "Incorrect username")
    @UserExists
    @Schema(description = "Username", example = "Samuel")
    private String username;

    @Pattern(regexp = PASSWORD_PATTERN, message = "Incorrect password")
    @NotBlank(message = PASSWORD_NOT_BLANK)
    @Schema(description = "Password", example = "MyPassword")
    private String password;

    @NotBlank(message = "Enter verify password")
    @Pattern(regexp = PASSWORD_PATTERN, message = "Incorrect verify password")
    @Schema(description = "Verified Password",  example = "MyPassword")
    private String verifyPassword;

}
