package com.javatemplate.api.book;

import org.junit.jupiter.api.Test;

import static com.javatemplate.api.book.BookDTOMapper.toBookResponseDTO;
import static com.javatemplate.api.book.BookDTOMapper.toBookResponseDTOs;
import static com.javatemplate.fakes.BookFakes.buildBook;
import static com.javatemplate.fakes.BookFakes.buildBooks;
import static org.junit.jupiter.api.Assertions.assertEquals;

class BookDTOMapperTest {

    @Test
    void shouldToBookResponseDTO_OK() {
        final var book = buildBook();
        final var bookDTO = toBookResponseDTO(book);

        assertEquals(book.getId(), bookDTO.getId());
        assertEquals(book.getName(), bookDTO.getName());
        assertEquals(book.getAuthor(), bookDTO.getAuthor());
        assertEquals(book.getUpdatedAt(), bookDTO.getUpdatedAt());
        assertEquals(book.getCreatedAt(), bookDTO.getCreatedAt());
        assertEquals(book.getDescription(), bookDTO.getDescription());
        assertEquals(book.getImage(), bookDTO.getImage());
    }

    @Test
    void shouldToBookResponseDTOs_Ok() {
        final var books = buildBooks();
        final var bookDTOs = toBookResponseDTOs(books);

        assertEquals(books.size(), bookDTOs.size());
    }
}
