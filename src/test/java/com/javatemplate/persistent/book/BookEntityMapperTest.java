package com.javatemplate.persistent.book;

import org.junit.jupiter.api.Test;

import static com.javatemplate.fakes.BookFakes.buildBookEntities;
import static com.javatemplate.fakes.BookFakes.buildBookEntity;
import static com.javatemplate.persistent.book.BookEntityMapper.toBook;
import static com.javatemplate.persistent.book.BookEntityMapper.toBooks;
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
}