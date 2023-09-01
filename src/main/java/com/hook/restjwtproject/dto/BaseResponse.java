package com.hook.restjwtproject.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public abstract class BaseResponse {
    @Schema(description = "Identifier", example = "200")
    private Integer status;
    @Schema(description = "Timestamp", example = "2023-12-01")
    private final LocalDate timestamp = LocalDate.now();

}
