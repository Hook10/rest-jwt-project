package com.hook.restjwtproject.controller;

import static com.hook.restjwtproject.utils.Constants.SECURITY_SWAGGER;
import static com.hook.restjwtproject.utils.Constants.USER;
import static com.hook.restjwtproject.utils.ResponseUtils.CHANGE_ROLE_MESSAGE;
import static com.hook.restjwtproject.utils.ResponseUtils.getSuccessResponse;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.hook.restjwtproject.dto.BaseResponse;
import com.hook.restjwtproject.dto.ExceptionResponse;
import com.hook.restjwtproject.dto.SuccessResponse;
import com.hook.restjwtproject.dto.UserDto;
import com.hook.restjwtproject.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@Tag(name = "Admin", description = "Admin management API")
@SecurityRequirement(name = SECURITY_SWAGGER)
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    @Operation(
            summary = "Set the administrator role for the user",
            description = "Update the user role by specifying its id. The response is a message about successful changed role",
            tags = "patch"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = SuccessResponse.class),
                    mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "403", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class),
                    mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class),
                    mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class),
                    mediaType = APPLICATION_JSON_VALUE)})})
    @PatchMapping("/set/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BaseResponse> setAdmin(@PathVariable("id") @Min(1) Long id) {
        adminService.setAdmin(id);
        return ResponseEntity.ok(getSuccessResponse(CHANGE_ROLE_MESSAGE, USER));
    }

    @Operation(
            summary = "Retrieve all users",
            description = "Collects all users. Returns an array of users with id, username, role for each elements",
            tags = "get"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = UserDto.class)),
                    mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "403", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class),
                    mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class),
                    mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class),
                    mediaType = APPLICATION_JSON_VALUE)})})
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(adminService.getAllUsers());
    }

}
