package com.javatemplate.persistent.book;

import org.junit.jupiter.api.Test;

import static com.javatemplate.fakes.BookFakes.*;
import static com.javatemplate.persistent.book.BookEntityMapper.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class BookEntityMapperTest {

    @Test
    void shouldToBook_Ok() {
        final var bookEntity = buildBookEntity();
        final var book = toBook(bookEntity);

        assertEquals(bookEntity.getId(), book.getId());
        assertEquals(bookEntity.getName(), book.getName());
    }

    @Test
    void shouldToBooks_Ok() {
        final var bookEntities = buildBookEntities();
        final var books = toBooks(bookEntities);

        assertEquals(bookEntities.size(), books.size());
    }

    @Test
    void shouldToBookEntity_Ok() {
        final var book = buildBook();
        final var bookEntity = toBookEntity(book);

        assertEquals(bookEntity.getId(), book.getId());
        assertEquals(bookEntity.getName(), book.getName());
    }
}
