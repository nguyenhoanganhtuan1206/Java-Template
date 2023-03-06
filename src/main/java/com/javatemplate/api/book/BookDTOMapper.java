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
                .name(book.getName())
                .author(book.getAuthor())
                .description(book.getDescription())
                .createdAt(book.getCreatedAt())
                .updatedAt(book.getUpdatedAt())
                .image(book.getImage())
                .userId(book.getUserId())
                .build();
    }

    public static List<BookDTO> toBookDTOs(final List<Book> books) {
        return emptyIfNull(books).stream()
                .map(BookDTOMapper::toBookDTO)
                .toList();
    }

    public static Book toBook(final BookDTO bookDTO) {
        return Book.builder()
                .id(bookDTO.getId())
                .name(bookDTO.getName())
                .author(bookDTO.getAuthor())
                .description(bookDTO.getDescription())
                .createdAt(bookDTO.getCreatedAt())
                .updatedAt(bookDTO.getUpdatedAt())
                .image(bookDTO.getImage())
                .userId(bookDTO.getUserId())
                .build();
    }
}
