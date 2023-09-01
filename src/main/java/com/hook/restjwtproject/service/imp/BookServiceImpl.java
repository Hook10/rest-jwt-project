package com.hook.restjwtproject.service.imp;

import static com.hook.restjwtproject.utils.ObjectUtils.getIgnoredProperties;

import com.hook.restjwtproject.domain.Book;
import com.hook.restjwtproject.dto.BookDto;
import com.hook.restjwtproject.exception.BookNotFoundException;
import com.hook.restjwtproject.mapper.BookMapper;
import com.hook.restjwtproject.repository.BookRepository;
import com.hook.restjwtproject.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Override
    public List<BookDto> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        return books.stream()
                .map(bookMapper::convertToDto)
                .toList();
    }

    @Override
    public BookDto getBook(Long id) {
        return bookMapper.convertToDto(bookRepository.getReferenceById(id));
    }

    @Override
    public void save(BookDto bookDto) {
        bookRepository.save(bookMapper.convertToDomain(bookDto));
    }

    @Override
    public void update(Long id, BookDto bookDto) {
        Book book = bookRepository.findById(id).orElseThrow(
                BookNotFoundException::new);
        Book updatedBook = bookMapper.convertToDomain(bookDto);
        BeanUtils.copyProperties(updatedBook, book, getIgnoredProperties(updatedBook, "id"));


    }

    @Override
    public void delete(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(
                BookNotFoundException::new);
        bookRepository.deleteById(book.getId());
    }
}
