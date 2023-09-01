package com.hook.restjwtproject.service;

import com.hook.restjwtproject.dto.BookDto;
import java.util.List;

public interface BookService {

    List<BookDto> getAllBooks();
    BookDto getBook(Long Id);
    void save(BookDto bookDto);
    void update(Long id, BookDto bookDto);
    void delete(Long id);
}
