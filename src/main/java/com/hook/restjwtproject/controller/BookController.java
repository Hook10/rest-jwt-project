package com.hook.restjwtproject.controller;

import static com.hook.restjwtproject.utils.Constants.BOOK;
import static com.hook.restjwtproject.utils.Constants.SECURITY_SWAGGER;
import static com.hook.restjwtproject.utils.ResponseUtils.CREATION_MESSAGE;
import static com.hook.restjwtproject.utils.ResponseUtils.DELETE_MESSAGE;
import static com.hook.restjwtproject.utils.ResponseUtils.UPDATE_MESSAGE;
import static com.hook.restjwtproject.utils.ResponseUtils.getSuccessResponse;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.hook.restjwtproject.dto.BaseResponse;
import com.hook.restjwtproject.dto.BookDto;
import com.hook.restjwtproject.dto.ErrorValidatorResponse;
import com.hook.restjwtproject.dto.ExceptionResponse;
import com.hook.restjwtproject.dto.SuccessResponse;
import com.hook.restjwtproject.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@Tag(name = "Book", description = "Book management API")
@SecurityRequirement(name = SECURITY_SWAGGER)
@RestController
@RequiredArgsConstructor
@RequestMapping("/book")
public class BookController {

    private final BookService bookService;

    @Operation(
            summary = "Retrieve all books",
            description = "Collect all books. Returns an array of book with id, author, title for each elements",
            tags = "get"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = BookDto.class)),
                    mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "403", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class),
                    mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class),
                    mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class),
                    mediaType = APPLICATION_JSON_VALUE)})})
    @GetMapping
    public ResponseEntity<List<BookDto>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @Operation(
            summary = "Retrieve a book by id",
            description = "Get book by id. The response is a book with identifier, author and title",
            tags = "get"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = BookDto.class),
                    mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "403", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class),
                    mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class),
                    mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class),
                    mediaType = APPLICATION_JSON_VALUE)})})
    @GetMapping("{id}")
    public ResponseEntity<BookDto> getBook(@PathVariable("id") @Min(1) Long id) {
        return ResponseEntity.ok(bookService.getBook(id));
    }

    @Operation(
            summary = "Create new book",
            description = "Creates a new book. The response is a message about the successfully creation of a book",
            tags = "post"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = SuccessResponse.class),
                    mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "400", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = ErrorValidatorResponse.class)),
                    mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "403", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class),
                    mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class),
                    mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class),
                    mediaType = APPLICATION_JSON_VALUE)})})
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BaseResponse> create(@RequestBody @Valid BookDto bookDto) {
        bookService.save(bookDto);
        return ResponseEntity.ok(getSuccessResponse(CREATION_MESSAGE, BOOK));
    }

    @Operation(
            summary = "Update book by id",
            description = "Update a book by id. The response is a message about the successfully update of a book",
            tags = "patch"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = SuccessResponse.class),
                    mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "400", content = {@Content(array = @ArraySchema(schema = @Schema(implementation = ErrorValidatorResponse.class)),
                    mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "403", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class),
                    mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class),
                    mediaType = APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema(implementation = ExceptionResponse.class),
                    mediaType = APPLICATION_JSON_VALUE)})})
    @PatchMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BaseResponse> update(@PathVariable("id") Long id, @RequestBody @Valid BookDto bookDto) {
        bookService.update(id, bookDto);
        return ResponseEntity.ok(getSuccessResponse(UPDATE_MESSAGE, BOOK));
    }

    @Operation(
            summary = "Delete book by id",
            description = "Delete a book by id. The response is a message book successfully deleted",
            tags = "delete"
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
    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BaseResponse> delete(@PathVariable("id") @Min(1) Long id) {
        bookService.delete(id);
        return ResponseEntity.ok(getSuccessResponse(DELETE_MESSAGE, BOOK));
    }

}
