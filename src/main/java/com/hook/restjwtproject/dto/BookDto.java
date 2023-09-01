package com.hook.restjwtproject.dto;

import static com.hook.restjwtproject.utils.Constants.AUTHOR_PATTERN;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@Schema(description = "Entity of Book")
public class BookDto extends BaseDto {

    @NotBlank(message = "Enter author")
    @Pattern(regexp = AUTHOR_PATTERN, message = "Incorrect author's name")
    @Schema(description = "Author", example = "Stephen King")
    private String author;

    @Schema(description = "Title", example = "The Dart Tower")
    @NotBlank(message = "Enter title")
    private String title;
}
