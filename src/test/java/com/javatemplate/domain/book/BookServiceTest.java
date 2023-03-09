package com.javatemplate.domain.book;

import com.javatemplate.error.BadRequestException;
import com.javatemplate.error.NotFoundException;
import com.javatemplate.persistent.book.BookStore;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.javatemplate.fakes.BookFakes.buildBook;
import static com.javatemplate.fakes.BookFakes.buildBooks;
import static java.util.UUID.randomUUID;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.assertj.core.util.Lists.emptyList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookStore bookStore;

    @InjectMocks
    private BookService bookService;

    @Test
    void shouldFindAll_OK() {
        final var expected = buildBooks();

        when(bookStore.findAll()).thenReturn(expected);

        final var actual = bookService.findAll();

        assertEquals(expected.size(), actual.size());
        assertEquals(expected.get(0).getId(), actual.get(0).getId());
        assertEquals(expected.get(0).getName(), actual.get(0).getName());
        assertEquals(expected.get(0).getAuthor(), actual.get(0).getAuthor());
        assertEquals(expected.get(0).getUserId(), actual.get(0).getUserId());
        assertEquals(expected.get(0).getUpdatedAt(), actual.get(0).getUpdatedAt());
        assertEquals(expected.get(0).getCreatedAt(), actual.get(0).getCreatedAt());
        assertEquals(expected.get(0).getImage(), actual.get(0).getImage());

        verify(bookStore).findAll();
    }

    @Test
    void shouldFindById_OK() {
        final var book = buildBook();

        when(bookStore.findById(book.getId())).thenReturn(Optional.of(book));

        assertEquals(book, bookService.findById(book.getId()));
        verify(bookStore).findById(book.getId());
    }

    @Test
    void shouldFindById_ThrownNotFound() {
        final var uuid = randomUUID();

        when(bookStore.findById(uuid)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> bookService.findById(uuid));
        verify(bookStore).findById(uuid);
    }


    @Test
    void shouldCreate_OK() {
        final var book = buildBook();

        when(bookStore.save(book)).thenReturn(book);

        assertEquals(book, bookService.create(book));
        verify(bookStore).save(book);
    }

    @Test
    void shouldCreate_ThrownBadRequest() {
        final var book = buildBook();
        book.setName(null);

        assertThrows(BadRequestException.class, () -> bookService.create(book));
    }


    @Test
    void shouldUpdate_OK() {
        final var book = buildBook();
        final var bookUpdate = buildBook();
        bookUpdate.setId(book.getId());

        when(bookStore.findById(book.getId())).thenReturn(Optional.of(book));
        when(bookStore.save(book)).thenReturn(book);

        final var actual = bookService.update(book.getId(), bookUpdate);

        assertEquals(actual.getId(), bookUpdate.getId());
        assertEquals(actual.getName(), bookUpdate.getName());
        assertEquals(actual.getAuthor(), bookUpdate.getAuthor());
        assertEquals(actual.getImage(), bookUpdate.getImage());
        assertEquals(actual.getCreatedAt(), bookUpdate.getCreatedAt());
        assertEquals(actual.getDescription(), bookUpdate.getDescription());
        assertEquals(actual.getUserId(), bookUpdate.getUserId());

        verify(bookStore).findById(book.getId());
    }

    @Test
    void shouldUpdate_ThrownBadRequest() {
        final var book = buildBook();
        final var bookUpdate = buildBook();
        bookUpdate.setId(book.getId());
        bookUpdate.setName(null);

        assertThrows(BadRequestException.class, () -> bookService.update(book.getId(), bookUpdate));
    }

    @Test
    void shouldUpdate_NotFound() {
        final var bookId = randomUUID();
        final var bookUpdate = buildBook();

        when(bookStore.findById(bookId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> bookService.update(bookId, bookUpdate));
        verify(bookStore).findById(bookId);
    }

    @Test
    void shouldDeleteId_OK() {
        final var book = buildBook();

        when(bookStore.findById(book.getId())).thenReturn(Optional.of(book));

        bookService.deleteById(book.getId());
        verify(bookStore).findById(book.getId());
    }

    @Test
    void shouldDeleteId_ThrownNotFound() {
        final var uuid = randomUUID();

        when(bookStore.findById(uuid)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> bookService.deleteById(uuid));
        verify(bookStore).findById(uuid);
    }

    @Test
    void shouldFindBooksByName_OK() {
        final var book = buildBook();
        final var expected = buildBooks();

        when(bookStore.findBooksByNameOrAuthor(anyString())).thenReturn(expected);

        final var actual = bookService.findBooksByNameOrAuthor(book.getName());

        assertEquals(actual.size(), expected.size());

        verify(bookStore).findBooksByNameOrAuthor(book.getName());
    }

    @Test
    void shouldFindBooksByName_Empty() {
        final var input = randomAlphabetic(3, 10);

        when(bookStore.findBooksByNameOrAuthor(input)).thenReturn(emptyList());

        final var actual = bookService.findBooksByNameOrAuthor(input);

        assertTrue(actual.isEmpty());

        verify(bookStore).findBooksByNameOrAuthor(input);
    }
}
