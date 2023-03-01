package com.javatemplate.api.book;

import org.junit.jupiter.api.Test;

import static com.javatemplate.api.book.BookDTOMapper.toBookDTO;
import static com.javatemplate.api.book.BookDTOMapper.toBookDTOs;
import static com.javatemplate.fakes.BookFakes.buildBook;
import static com.javatemplate.fakes.BookFakes.buildBooks;
import static org.junit.jupiter.api.Assertions.assertEquals;

class BookDTOMapperTest {

    @Test
    void shouldToBookDTO_Ok() {
        final var book = buildBook();
        final var bookDTO = toBookDTO(book);

        assertEquals(book.getId(), bookDTO.getId());
        assertEquals(book.getName(), bookDTO.getName());
    }

    @Test
    void shouldToBookDTOs_Ok() {
        final var books = buildBooks();
        final var bookDTOs = toBookDTOs(books);

        assertEquals(books.size(), bookDTOs.size());
    }
}