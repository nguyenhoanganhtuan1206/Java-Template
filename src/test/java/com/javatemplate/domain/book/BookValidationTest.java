package com.javatemplate.domain.book;

import com.javatemplate.error.BadRequestException;
import org.junit.jupiter.api.Test;

import static com.javatemplate.domain.book.BookValidation.validateBook;
import static com.javatemplate.fakes.BookFakes.buildBook;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BookValidationTest {

    @Test
    void validate_OK() {
        final var book = buildBook()
                .withRating(3.5)
                .withYear(2023);

        assertDoesNotThrow(() -> validateBook(book));
    }

    @Test
    void validate_ThrowNameEmpty() {
        final var book = buildBook();
        book.setName(null);

        assertThrows(BadRequestException.class, () -> validateBook(book));
    }

    @Test
    void validate_ThrowAuthorEmpty() {
        final var book = buildBook()
                .withAuthor(null);

        assertThrows(BadRequestException.class, () -> validateBook(book));
    }

    @Test
    void validate_ThrowSubtitleEmpty() {
        final var book = buildBook()
                .withSubtitle(null);

        assertThrows(BadRequestException.class, () -> validateBook(book));
    }

    @Test
    void validate_ThrowPublisherEmpty() {
        final var book = buildBook()
                .withPublisher(null);

        assertThrows(BadRequestException.class, () -> validateBook(book));
    }

    @Test
    void validate_ThrowIsbn13Empty() {
        final var book = buildBook()
                .withIsbn13(null);

        assertThrows(BadRequestException.class, () -> validateBook(book));
    }

    @Test
    void validate_ThrowInvalidIsbn13Length() {
        final var book = buildBook()
                .withIsbn13(randomAlphabetic(5));

        assertThrows(BadRequestException.class, () -> validateBook(book));
    }

    @Test
    void validate_ThrowPriceEmpty() {
        final var book = buildBook()
                .withPrice(null);

        assertThrows(BadRequestException.class, () -> validateBook(book));
    }

    @Test
    void validate_ThrowYearEmpty() {
        final var book = buildBook()
                .withYear(null);

        assertThrows(BadRequestException.class, () -> validateBook(book));
    }

    @Test
    void validate_ThrowInvalidYear() {
        final var book = buildBook()
                .withYear(2024);

        assertThrows(BadRequestException.class, () -> validateBook(book));
    }

    @Test
    void validate_ThrowInvalidRating() {
        final var book = buildBook()
                .withRating(6.0);

        assertThrows(BadRequestException
                .class, () -> validateBook(book));
    }
}
