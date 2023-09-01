package com.hook.restjwtproject.mapper;

import com.hook.restjwtproject.domain.Book;
import com.hook.restjwtproject.dto.BookDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = BaseMapper.class)
public interface BookMapper {

    BookDto convertToDto(Book book);
    Book convertToDomain(BookDto bookDto);

}
