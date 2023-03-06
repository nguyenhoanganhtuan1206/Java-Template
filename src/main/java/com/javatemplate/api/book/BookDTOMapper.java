package com.javatemplate.api.book;

import com.javatemplate.domain.book.Book;
import lombok.experimental.UtilityClass;

import java.util.List;

import static org.apache.commons.collections4.ListUtils.emptyIfNull;

@UtilityClass
public class BookDTOMapper {

    public static BookDTO toBookDTO(final Book book) {
        return BookDTO.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .description(book.getDescription())
                .createAt(book.getCreateAt())
                .updateAt(book.getUpdateAt())
                .image(book.getImage())
                .userId(book.getUserId())
                .build();
    }

    public static List<BookDTO> toBookDTOs(final List<Book> books) {
        return emptyIfNull(books).stream()
                .map(BookDTOMapper::toBookDTO)
                .toList();
    }
}
