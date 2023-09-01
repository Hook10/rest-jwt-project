package com.hook.restjwtproject.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class JwtResponse {
    @Schema(description = "Jwt-token", example = "xxx.yyyy.zzzzz")
    private String token;

}
