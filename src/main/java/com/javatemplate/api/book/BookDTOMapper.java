package com.javatemplate.api.book;

import com.javatemplate.domain.book.Book;
import lombok.experimental.UtilityClass;

import java.util.List;

import static org.apache.commons.collections4.ListUtils.emptyIfNull;

@UtilityClass
public class BookDTOMapper {

    public static BookResponseDTO toBookDTO(final Book book) {
        return BookResponseDTO.builder()
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

    public static List<BookResponseDTO> toBookDTOs(final List<Book> books) {
        return emptyIfNull(books).stream()
                .map(BookDTOMapper::toBookDTO)
                .toList();
    }

    public static Book toBookCreateRequestDTO(final BookCreateRequestDTO bookDTO) {
        return Book.builder()
                .name(bookDTO.getName())
                .author(bookDTO.getAuthor())
                .description(bookDTO.getDescription())
                .image(bookDTO.getImage())
                .userId(bookDTO.getUserId())
                .build();
    }

    public static Book toBookUpdateRequestDTO(final BookUpdateRequestDTO bookDTO) {
        return Book.builder()
                .name(bookDTO.getName())
                .author(bookDTO.getAuthor())
                .description(bookDTO.getDescription())
                .image(bookDTO.getImage())
                .userId(bookDTO.getUserId())
                .build();
    }
}
