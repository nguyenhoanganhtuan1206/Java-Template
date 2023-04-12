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
        assertEquals(bookEntity.getAuthor(), book.getAuthor());
        assertEquals(bookEntity.getUpdatedAt(), book.getUpdatedAt());
        assertEquals(bookEntity.getCreatedAt(), book.getCreatedAt());
        assertEquals(bookEntity.getDescription(), book.getDescription());
        assertEquals(bookEntity.getSubtitle(), book.getSubtitle());
        assertEquals(bookEntity.getPublisher(), book.getPublisher());
        assertEquals(bookEntity.getIsbn13(), book.getIsbn13());
        assertEquals(bookEntity.getPrice(), book.getPrice());
        assertEquals(bookEntity.getYear(), book.getYear());
        assertEquals(bookEntity.getRating(), book.getRating());
        assertEquals(bookEntity.getImage(), book.getImage());
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

        assertEquals(book.getId(), bookEntity.getId());
        assertEquals(book.getName(), bookEntity.getName());
        assertEquals(book.getSubtitle(), bookEntity.getSubtitle());
        assertEquals(book.getPublisher(), bookEntity.getPublisher());
        assertEquals(book.getIsbn13(), bookEntity.getIsbn13());
        assertEquals(book.getPrice(), bookEntity.getPrice());
        assertEquals(book.getYear(), bookEntity.getYear());
        assertEquals(book.getRating(), bookEntity.getRating());
        assertEquals(book.getImage(), bookEntity.getImage());
    }
}
