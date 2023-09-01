package com.hook.restjwtproject.dto;

import static com.hook.restjwtproject.utils.Constants.ROLE_USER;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@Schema(description = "Entity of User")
public class UserDto extends BaseDto {

    @Schema(description = "Username", example = "Samuel")
    private String username;

    @Schema(description = "User Role", example = ROLE_USER)
    private String role;

}
