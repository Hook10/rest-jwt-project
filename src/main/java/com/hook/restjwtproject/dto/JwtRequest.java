package com.hook.restjwtproject.dto;

import static com.hook.restjwtproject.utils.Constants.PASSWORD_NOT_BLANK;
import static com.hook.restjwtproject.utils.Constants.USERNAME_NOT_BLANK;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;

@Getter
@Setter
@Schema(description = "Entity of User")
public class JwtRequest {

    @NotBlank(message = USERNAME_NOT_BLANK)
    @Schema(description = "Username", example = "Samuel")
    private String username;

    @NotBlank(message = PASSWORD_NOT_BLANK)
    @Schema(description = "Password", example = "mypassword")
    private String password;

}
