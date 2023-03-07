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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
    void shouldFindById_Ok() {
        final var book = buildBook();

        when(bookStore.findById(book.getId())).thenReturn(Optional.of(book));

        assertEquals(book, bookService.findById(book.getId()));
        verify(bookStore).findById(book.getId());
    }

    @Test
    void shouldFindById_Thrown() {
        final var uuid = randomUUID();

        when(bookStore.findById(uuid)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> bookService.findById(uuid));
        verify(bookStore).findById(uuid);
    }


    @Test
    void shouldCreate_Ok() {
        final var book = buildBook();

        when(bookStore.save(book)).thenReturn(book);

        assertEquals(book, bookService.create(book));
        verify(bookStore).save(book);
    }

    @Test
    void shouldCreate_Thrown() {
        final var book = buildBook();
        book.setName(null);

        assertThrows(BadRequestException.class, () -> bookService.create(book));
    }


    @Test
    void shouldUpdate_Ok() {
        final var book = buildBook();
        final var bookUpdate = buildBook();
        bookUpdate.setId(book.getId());

        when(bookStore.findById(book.getId())).thenReturn(Optional.of(book));
        when(bookStore.save(book)).thenReturn(book);

        final var expected = bookService.update(book.getId(), bookUpdate);

        assertEquals(expected.getId(), bookUpdate.getId());
        assertEquals(expected.getName(), bookUpdate.getName());
        assertEquals(expected.getAuthor(), bookUpdate.getAuthor());
        assertEquals(expected.getImage(), bookUpdate.getImage());
        assertEquals(expected.getCreatedAt(), bookUpdate.getCreatedAt());
        assertEquals(expected.getDescription(), bookUpdate.getDescription());
        assertEquals(expected.getUserId(), bookUpdate.getUserId());

        verify(bookStore).findById(book.getId());
    }

    @Test
    void shouldUpdate_Thrown() {
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
    void shouldDeleteId_Ok() {
        final var book = buildBook();

        when(bookStore.findById(book.getId())).thenReturn(Optional.of(book));

        bookService.deleteById(book.getId());
        verify(bookStore).findById(book.getId());
    }

    @Test
    void shouldDeleteId_Thrown() {
        final var uuid = randomUUID();

        when(bookStore.findById(uuid)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> bookService.deleteById(uuid));
        verify(bookStore).findById(uuid);
    }
}
